package com.example.androidnotifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.session.MediaSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.media.session.MediaSessionCompat
import android.view.ScrollCaptureSession
import android.view.View
import android.widget.EditText
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.androidnotifications.App.Companion.CHANNEL_1_ID
import com.example.androidnotifications.App.Companion.CHANNEL_2_ID
import com.example.androidnotifications.App.Companion.CHANNEL_3_ID

class MainActivity : AppCompatActivity() {

    private lateinit var notificationManagerCompat: NotificationManagerCompat
    private lateinit var editTextTitle:EditText
    private lateinit var editTextMessage:EditText

    // To make notification of background colour of media

    private lateinit var mediaSession: MediaSessionCompat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManagerCompat = NotificationManagerCompat.from(this)
        editTextTitle = findViewById(R.id.edit_text_title)
        editTextMessage = findViewById(R.id.edit_text_message)

        mediaSession = MediaSessionCompat(this,"tag")


    }

    fun sendOnChannel1(view:View){

        val title = editTextTitle.text.toString()
        val message = editTextMessage.text.toString()


        // Making this to open a new activity on clicking the notification
        val activityIntent = Intent(this,MainActivity::class.java)
         // Notification manager requires pemding intent
        val contentIntent:PendingIntent = PendingIntent.getActivity(this,0,activityIntent,0)


        // Action button se send a new Broadcast
        val broadcastIntent = Intent(this,NotificationReceiever::class.java)
        broadcastIntent.putExtra("toastMessage",message)
        val actionButtonIntent = PendingIntent.getBroadcast(this,0,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT)

            // To add bitmap to notification
        val bitmap:Bitmap = BitmapFactory.decodeResource(resources,R.drawable.dog)

        val notification = NotificationCompat.Builder(this,CHANNEL_1_ID)
            .setSmallIcon(R.drawable.ic_one)
            .setContentTitle(title)
            .setContentText(message)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .addAction(R.mipmap.ic_launcher,"TOAST",actionButtonIntent)// Adding action button in notification
            .setContentIntent(contentIntent)  // TO open an activity on clicking the message
            .setAutoCancel(true)  // TO dismiss the notification on clicking
            .setColor(Color.BLUE) // Coloutr of notification
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(bitmap) // TO set image

            // To show big text
//            .setStyle(NotificationCompat.BigTextStyle()
//                .bigText("nghngvhnfvf h nfffnfhhg hnvhuenuhvnnunu This column contains things that can help you build some complex use cases like calling a server, saving user data locally, showing an image from a server within a good code structure."
//                    ).setBigContentTitle("BIG CONTENT TITLE")
//                .setSummaryText("Summary Text"))

                // Big Picture Style
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null))
            .build()

        notificationManagerCompat.notify(1,notification)
    }

    fun sendOnChannel2(view:View){

//        notificationConcepts()


        // NOTIFICATION GROUP IMPLEMENTATION

        notificationGroups()




    }

    private fun notificationGroups(){

//        val title = editTextTitle.text.toString()
//        val message = editTextMessage.text.toString()
//
//
//
//        val notification = NotificationCompat.Builder(this,CHANNEL_2_ID)
//            .setSmallIcon(R.drawable.ic_two)
//            .setContentTitle(title)
//            .setContentText(message)
//            .build()



        // 4 or more notifications are sent in group by default android Nougat onwards

//        for(i in 0..5){
//            SystemClock.sleep(2000)
//            notificationManagerCompat.notify(i,notification)
//        }

        // Now we will group 2 notifications in notificatiosn groups

        val title1 = "title1"
        val message1= "message1"

        val title2 = "title2"
        val message2= "message2"

        val notification1 = NotificationCompat.Builder(this,CHANNEL_2_ID)
            .setSmallIcon(R.drawable.ic_two)
            .setContentTitle(title1)
            .setContentText(message1)
            .setGroup("example_group")
            .build()

        val notification2 = NotificationCompat.Builder(this,CHANNEL_2_ID)
            .setSmallIcon(R.drawable.ic_two)
            .setContentTitle(title2)
            .setContentText(message2)
            .setGroup("example_group")
            .build()

        val summaryNotification = NotificationCompat.Builder(this,CHANNEL_2_ID)
            .setSmallIcon(R.drawable.ic_two)
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("$title2  $message2")
                .addLine("$title1   $message2")
                .setBigContentTitle("2 new messages"))
            .setSubText("user@examole.com")
            .setGroupSummary(true)
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
            .setGroup("example_group")
            .build()

        notificationManagerCompat.notify(2,notification1)
        notificationManagerCompat.notify(3,notification2)
        notificationManagerCompat.notify(4,summaryNotification)




    }

    private fun notificationConcepts(){
        val title = editTextTitle.text.toString()
        val message = editTextMessage.text.toString()

        val artWork:Bitmap = BitmapFactory.decodeResource(resources,R.drawable.dog)

        // TO add a progress bar to the notifiction

        val progressMax = 100


        val notification = NotificationCompat.Builder(this,CHANNEL_2_ID)
            .setSmallIcon(R.drawable.ic_two)
            .setContentTitle(title)
            .setContentText(message)
//            .setStyle(NotificationCompat.InboxStyle()
//                .addLine("This is line 1")
//            .addLine("This is line 2")
//        .addLine("This is line 3")
//        .addLine("This is line 4")
//        .addLine("This is line 5")
//        .addLine("This is line 6")
//        .addLine("This is line 7")
//            .setBigContentTitle("BIG CONTENT TITLE")
//            .setSummaryText("Summary Text"))
//        .setCategory(NotificationCompat.CATEGORY_MESSAGE)

            // MEDIA STYLE
            .setLargeIcon(artWork)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(1,2,3) // TO show next Pause and Previous // Only 3 wiil work here
                .setMediaSession(mediaSession.sessionToken))  // Media background / coloured notification
            .addAction(R.drawable.ic_one,"Like",null)
            .addAction(R.drawable.ic_next,"Next",null)
            .addAction(R.drawable.ic_pause,"Pause",null)
            .addAction(R.drawable.ic_previous,"Previous",null)
            .addAction(R.drawable.ic_stop,"Dislike",null)
            .setSubText("SUB TEXT")
            .setProgress(progressMax,0,false)  // Set progress bar


        notificationManagerCompat.notify(2,notification.build())

        // Update notification bar from the thread

//        val runnable = ExampleRunnable(notification,notificationManagerCompat)
//
//        Thread(runnable).start()
//
//        runnable.run()

    }

    fun sendOnChannel3(view:View) {


        val artWork:Bitmap = BitmapFactory.decodeResource(resources,R.drawable.dog)

        val collapsedView = RemoteViews(packageName,R.layout.notification_collapsed)
        val expandedView = RemoteViews(packageName,R.layout.notification_expanded)


        collapsedView.setTextViewText(R.id.text_collapsed,"HelloWorld")

        val notification = NotificationCompat.Builder(this,CHANNEL_3_ID)
            .setSmallIcon(R.drawable.ic_two)
            .setCustomContentView(collapsedView)
            .setCustomBigContentView(expandedView)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle()) // Border around notification
            .build()

        notificationManagerCompat.notify(3,notification)


    }


    class ExampleRunnable(val notification:NotificationCompat.Builder,val notificationManagerCompat:NotificationManagerCompat):Runnable{
        override fun run() {
            SystemClock.sleep(2000)
            val progressMax = 100

            for( progress in 0..progressMax+1 step 10){
                notification.setProgress(progressMax,progress,false)
                notificationManagerCompat.notify(2,notification.build())
            }

        }

    }





    }