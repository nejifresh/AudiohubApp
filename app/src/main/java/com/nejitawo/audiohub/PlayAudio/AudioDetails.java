package com.nejitawo.audiohub.PlayAudio;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jean.jcplayer.JcPlayerView;
import com.nejitawo.audiohub.Adapters.SongAdapter;
import com.nejitawo.audiohub.Util.GlobalClass;
import com.nejitawo.audiohub.Model.Title;
import com.nejitawo.audiohub.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ProgressCallback;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AudioDetails extends AppCompatActivity implements
        Runnable, SeekBar.OnSeekBarChangeListener,
        MediaPlayer.OnCompletionListener,
        AudioContract{

    CircleImageView artistImage;
    TextView songTitle, artist;
    JcPlayerView jcPlayerView;
    RelativeLayout playButton;
    ImageView ctrl;
    private SeekBar seekBar;
    private    MediaPlayer player;
    private ListView listView1;
    List<ParseObject> ob;
    ProgressDialog pDialog;
    private ImageView downloadClick;
    private int downbalance = 0;

    AudioPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        presenter = new AudioPresenter(this);

        artistImage = (CircleImageView)findViewById(R.id.interactivePlayerView);
        songTitle = (TextView)findViewById(R.id.musicTitle);
        artist = (TextView)findViewById(R.id.musicArtistName);
        listView1 = (ListView)findViewById(R.id.usersListView);
        final GlobalClass globalVariable = (GlobalClass)getApplicationContext();
        presenter.setupViewLoading(globalVariable.getAuthor());

        ctrl     = (ImageView)findViewById(R.id.control);

        seekBar = (SeekBar) findViewById(R.id.seekBar1);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setEnabled(false);

downloadClick = (ImageView)findViewById(R.id.downdown) ;
        downloadClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadClick.setEnabled(false);
                //Download the audio file and play when done
                presenter.downloadAudioFile(globalVariable.getTitle(),
                        globalVariable.getDownloads(),
                        globalVariable.getStoragemode(),
                        globalVariable.getFileLocation());

            }
        });

        playButton = (RelativeLayout)findViewById(R.id.btnPlayNow) ;
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Play file from device or from stream
                presenter.playAudioFile(globalVariable.getTitle(),
                        globalVariable.getTitleId(),
                        globalVariable.getStoragemode(),
                        globalVariable.getFileLocation());


            }
        });

    new loadData().execute();
    }

    public void run(){
        int currentPosition = player.getCurrentPosition();
        int total = player.getDuration();

        while(player!=null && currentPosition < total){
            try{
                Thread.sleep(1000);
                currentPosition = player.getCurrentPosition();
            } catch (InterruptedException e){
                return;
            } catch (Exception e){

                e.printStackTrace();
                return;
            }

            seekBar.setProgress(currentPosition);
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        try{
            if (player.isPlaying() || player!=null){
                if (fromUser)
                    player.seekTo(progress);
            }else if(player==null){
                seekBar.setProgress(0);
            }
        }catch (Exception e){
            Log.e("seek bar", "" + e);
            seekBar.setEnabled(false);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        seekBar.setEnabled(false);
        seekBar.setProgress(0);
        ctrl.setBackgroundDrawable(getResources(). getDrawable(R.drawable.ic_action_play) );
        mediaPlayer.reset();
        downloadClick.setEnabled(true);
     //   mediaPlayer.stop();
       // mediaPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            player.release();
            player = null;
        } catch (Exception e){

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_checkout) {
          //download
            //  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUpView(String artistTitle) {
        final GlobalClass globalVariable = (GlobalClass)getApplicationContext();
        Glide.with(this).load(globalVariable.getImageLocation()).into(artistImage);
        songTitle.setText(globalVariable.getTitle());
        artist.setText("Artist/Composer:  " + artistTitle);
    }

    @Override
    public void playAudio(String fileTitle, String titleId, String storageMade, String fileLocation) {

        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + fileTitle);
        if (file.exists()) {
            downloadClick.setEnabled(false);

            Toast.makeText(getApplicationContext(), "File already downloaded, playing track from device..", Toast.LENGTH_LONG).show();
            playTrack();
        } else {
            if (player == null) {
                player = new MediaPlayer();
                player.setOnCompletionListener(AudioDetails.this);
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //prepare the player and start
                seekBar.setEnabled(true);
                ctrl.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_action_pause));
                if (storageMade.equals("link")) {
                    //Stream from link
                    try {
                        player.setDataSource(fileLocation);
                        player.prepare();
                        player.start();
                        seekBar.setMax(player.getDuration());
                        new Thread(AudioDetails.this).start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else{
                    //Stream from Parse File
                    ParseQuery<ParseObject> linkQuery = new ParseQuery<ParseObject>("Titles");
                    linkQuery.getInBackground(titleId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e==null ){
                                String playLink = object.getParseFile("filename").getUrl();
                                try {
                                    player.setDataSource(playLink);
                                    player.prepare();
                                    player.start();
                                    seekBar.setMax(player.getDuration());
                                    new Thread(AudioDetails.this).start();

                                } catch (Exception ex) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }

            } else {
                //Now check if its already playing
                if (player.isPlaying()) {

                    try {
                        player.pause();
                        ctrl.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_action_play));
                        seekBar.setEnabled(false);
                        // player.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ctrl.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_action_pause));

                        player.start();
                        seekBar.setEnabled(true);
                        new Thread(AudioDetails.this).start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }

    @Override
    public void downloadAudioFile(String filetTitle,int totalDownloads, String storageMade, String fileLocation) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + filetTitle);
        if (file.exists()){
            Toast.makeText(getApplicationContext(), "File already downloaded, playing from device storage", Toast.LENGTH_LONG).show();
            playTrack();
        } else{

            if (totalDownloads == 0 ){
                //you cannot download this track
                showNoDownload();
            } else {
                //now decide which download routine to use
                if (storageMade.equals("link")) {

                    new DownloadMusic().execute(fileLocation);
                } else{
                    //Use parse mode
                    downloadParse();
                    Toast.makeText(AudioDetails.this,"Starting Download",Toast.LENGTH_LONG).show();
                }
            }

        }
    }



    public class loadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           /* progressView.setVisibility(View.VISIBLE);
            progressView.startAnimation();*/


        }

        @Override
        protected Void doInBackground(Void... params) {
            final GlobalClass globalClass =(GlobalClass)getApplicationContext();


            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "Titles");
            query.orderByDescending("_created_at");
            query.whereStartsWith("author", globalClass.getAuthor());



            try{
                ob = query.find();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            List<Title> myChoice = new ArrayList<Title>();
            for (ParseObject choice : ob){
                try {
                    Title t = Title.giveFullDetails(choice, getApplicationContext());
                    if (t != null) {
                        myChoice.add(t);
                    }
                    setupListView(myChoice);

                } catch(Exception e){
                    e.printStackTrace();
                    //Log.e("error",e.getMessage().toString());
                }


            }

           /* progressView.stopAnimation();
            progressView.setVisibility(View.INVISIBLE);*/

        }
    }

    public void setupListView(final List theChoices)  {
       runOnUiThread(new Runnable() {
            public void run() {
                List<Title> mChoices = theChoices;
                SongAdapter adapter = new SongAdapter(getApplicationContext(), mChoices);
                //listView1.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                       if (player!=null){
                            player.stop();
                        }
                        Title thisTitle = (Title)adapterView.getItemAtPosition(position);
                        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

                        globalVariable.setTitle(thisTitle.getTitle());
                        globalVariable.setAuthor(thisTitle.getAuthor());
                        globalVariable.setFileLocation(thisTitle.getFileLocation());
                        globalVariable.setImageLocation(thisTitle.getImageLocation());
                        globalVariable.setTitleId(thisTitle.getTitleId());
                        globalVariable.setCategory(thisTitle.getCategory());
                        globalVariable.setDocType(thisTitle.getDocType());
                        globalVariable.setStoragemode(thisTitle.getStoragemode());
                        Intent intent = new Intent(getApplicationContext(), AudioDetails.class);

                        startActivity(intent);
                        finish();


                    }
                });
                listView1.setAdapter(adapter);

            }
        });


    }

    class DownloadMusic extends AsyncTask<String,String, String>{
        final GlobalClass globalClass = (GlobalClass)getApplicationContext();
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(AudioDetails.this);
            pDialog.setMessage("Downloading File.. Please wait");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setMax(100);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... durl){
            int count;
            try{
                URL url = new URL(durl[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                //Get the length of music file
                int fileLength = connection.getContentLength();
                InputStream inputStream = new BufferedInputStream(url.openStream(),10*1024);
                //output stream to write file in SD Card
                OutputStream outputStream =
                        new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/" + globalClass.getTitle());
                byte data[] = new byte[1024];
                long total = 0;

                while((count = inputStream.read(data)) != -1){
                    total+=count;
                    //publish the prgroress which triggers onProgressUpdate METHOD
                    publishProgress("" + (int) (total * 100)/fileLength);
                    outputStream.write(data,0,count);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();

            } catch (Exception e){
                Log.e("download error", e.getMessage());
            }

            return  null;
        }

        //Show the prgress while downloading the file
        protected void onProgressUpdate(String... progress){
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url){
            pDialog.dismiss();
            ParseQuery<ParseObject> downloadsQuery = new ParseQuery<ParseObject>("Downloads");
            downloadsQuery.whereEqualTo("user",ParseUser.getCurrentUser().getUsername());
            downloadsQuery.whereEqualTo("titleid",globalClass.getTitleId());
            downloadsQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e==null){
                        if (objects.size()>0){
                            //Already downloaded this song so do not debit it from his downloads balance

                        } else{



    //User has not downloaded this song so record and debit his download balance
    ParseObject downloads = new ParseObject("Downloads");
    downloads.put("user",ParseUser.getCurrentUser().getUsername());
    downloads.put("title",globalClass.getTitle());
    downloads.put("author",globalClass.getAuthor());
    downloads.put("filelocation",globalClass.getFileLocation());
    downloads.put("imagelocation",globalClass.getImageLocation());
    downloads.put("category",globalClass.getCategory());
    downloads.put("doctype",globalClass.getDocType());
    downloads.put("titleid",globalClass.getTitleId());
    downloads.saveEventually();



                            ParseQuery<ParseObject> downQuery = new ParseQuery<ParseObject>("Player");
                            downQuery.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
                            downQuery.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> objects, ParseException e) {
                                    if (e==null & objects.size()>0){
                                        for (ParseObject ob: objects){
                                            downbalance = ob.getInt("downloads");
                                            ob.put("downloads",downbalance-1);
                                            ob.saveEventually();
                                        }


                                    }
                                }
                            });




                        }
                    }
                }
            });

            Toast.makeText(getApplicationContext(),"Download Completed", Toast.LENGTH_SHORT).show();
            //downloadClick.setEnabled(true);
            playTrack();
        }
    }

    private void downloadParse(){

        pDialog = new ProgressDialog(AudioDetails.this);
        pDialog.setMessage("Downloading File.. Please wait");
        pDialog.setIndeterminate(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setMax(100);
        pDialog.setCancelable(false);
        pDialog.show();

        final GlobalClass globalClass = (GlobalClass)getApplicationContext();
        ParseQuery<ParseObject> titles = new ParseQuery<ParseObject>("Titles");



        titles.getInBackground(globalClass.getTitleId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                    ParseFile file = (ParseFile)object.getParseFile("filename");
                    file.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {

                            //Write the bytes to file
                            try{
                                OutputStream outputStream =
                                        new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/" + globalClass.getTitle());

                                outputStream.write(data,0,data.length);
                                outputStream.flush();
                                outputStream.close();
                                pDialog.dismiss();

                                ParseQuery<ParseObject> downloadsQuery = new ParseQuery<ParseObject>("Downloads");
                                downloadsQuery.whereEqualTo("user",ParseUser.getCurrentUser().getUsername());
                                downloadsQuery.whereEqualTo("titleid",globalClass.getTitleId());
                                downloadsQuery.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if(e==null){
                                            if (objects.size()>0){
                                                //Already downloaded this song so do not debit it from his downloads balance

                                            } else{



                                                //User has not downloaded this song so record and debit his download balance
                                                ParseObject downloads = new ParseObject("Downloads");
                                                downloads.put("user",ParseUser.getCurrentUser().getUsername());
                                                downloads.put("title",globalClass.getTitle());
                                                downloads.put("author",globalClass.getAuthor());
                                                downloads.put("filelocation",globalClass.getFileLocation());
                                                downloads.put("imagelocation",globalClass.getImageLocation());
                                                downloads.put("category",globalClass.getCategory());
                                                downloads.put("doctype",globalClass.getDocType());
                                                downloads.put("titleid",globalClass.getTitleId());
                                                downloads.saveEventually();



                                                ParseQuery<ParseObject> downQuery = new ParseQuery<ParseObject>("Player");
                                                downQuery.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
                                                downQuery.findInBackground(new FindCallback<ParseObject>() {
                                                    @Override
                                                    public void done(List<ParseObject> objects, ParseException e) {
                                                        if (e==null & objects.size()>0){
                                                            for (ParseObject ob: objects){
                                                                downbalance = ob.getInt("downloads");
                                                                ob.put("downloads",downbalance-1);
                                                                ob.saveEventually();
                                                            }


                                                        }
                                                    }
                                                });




                                            }
                                        }
                                    }
                                });

                                Toast.makeText(getApplicationContext(),"Download Completed", Toast.LENGTH_SHORT).show();
                                //downloadClick.setEnabled(true);
                                playTrack();



                            }catch (Exception el){
                                pDialog.dismiss();
                                e.printStackTrace();
                            }



                        }

                    }, new ProgressCallback() {
                        @Override
                        public void done(Integer percentDone) {
                            pDialog.setProgress(percentDone);
                        }
                    });
                }
            }
        });
    }

    protected void playTrack(){
        final GlobalClass globalClass = (GlobalClass)getApplicationContext();
        //Read the file stored in SD CARD
        Uri myUri = Uri.parse("file:///sdcard/" + globalClass.getTitle());
        if (player == null){
            player = new MediaPlayer();
            player.setOnCompletionListener(AudioDetails.this);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //prepare the player and start
            seekBar.setEnabled(true);
            ctrl.setBackgroundDrawable(getResources(). getDrawable(R.drawable.ic_action_pause) );

            try{
                player.setDataSource(getApplicationContext(),myUri);
                player.prepare();
                player.start();
                seekBar.setMax(player.getDuration());
                new Thread(AudioDetails.this).start();

            } catch (Exception e){
                e.printStackTrace();
            }
        } else{
            //Now check if its already playing
            if (player.isPlaying()) {

                try {
                    player.pause();
                    ctrl.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_action_play));
                    seekBar.setEnabled(false);
                    // player.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ctrl.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_action_pause));

                    player.start();
                    seekBar.setEnabled(true);
                    new Thread(AudioDetails.this).start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }




    }


    private void showNoDownload(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AudioDetails.this);
        builder.setTitle("No Download Units");
        builder.setMessage("Sorry you have exhausted your download units, you can only stream this track");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              dialogInterface.dismiss();
            }
        });
     builder.create();
        builder.show();
    }
}
