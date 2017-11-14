package edu.dartmouth.cs.notificationchannel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private static final String GROUP_COURSE_NOTES = "course_notes_group";
	private static final String GROUP_COURSE_GRADES = "course_grade_group";
	private static final String GROUP_COURSE_FAQ = "course_faq_group";

	private static final String CHANNEL_COURSE_NOTES = "course_notes_channel";
	private static final String CHANNEL_COURSE_GRADES = "course_grade_channel";
	private static final String CHANNEL_COURSE_FAQ = "course_faq_channel";

	private static final int NOTIFICATION_ID_NOTES = 888;
	private static final int NOTIFICATION_ID_GRADES = 889;
	private static final int NOTIFICATION_ID_FAQ = 890;

	NotificationManager notificationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		notificationManager = getSystemService(NotificationManager.class);

		if (notificationManager.getNotificationChannel(CHANNEL_COURSE_NOTES) == null) {
			initNotificationGroups();
			initNotesChannel();
			initGradesChannel();
			initFAQChannel();
		}
	}

	private void initNotificationGroups() {
		ArrayList<NotificationChannelGroup> notificationChannelGroups = new ArrayList<>();

		notificationChannelGroups.add(new NotificationChannelGroup(GROUP_COURSE_NOTES, "CS65 class notes"));
		notificationChannelGroups.add(new NotificationChannelGroup(GROUP_COURSE_FAQ, "CS65 class faq"));
		notificationChannelGroups.add(new NotificationChannelGroup(GROUP_COURSE_GRADES, "CS65 class grades"));

		// createNotificationChannels
		notificationManager.createNotificationChannelGroups(notificationChannelGroups);
	}

	public void buildNotesNotification(View view) {
		Notification notification = new Notification.Builder(MainActivity.this, CHANNEL_COURSE_NOTES)
				.setContentTitle("New Class Notes!")
				.setContentText("Let's talk about notification channel today!")
				.setSmallIcon(android.R.drawable.stat_notify_sync)
				.build();

		notificationManager.notify(NOTIFICATION_ID_NOTES, notification);
	}

	public void buildGradesNotification(View view) {
		Notification notification = new Notification.Builder(MainActivity.this, CHANNEL_COURSE_GRADES)
				.setContentTitle("Final grade is ready!")
				.setContentText("Sergey: 67")
				.setSmallIcon(android.R.drawable.stat_sys_warning)
				.setBadgeIconType(Notification.BADGE_ICON_SMALL)
				.setColor(Color.RED)
				.setColorized(true)
				.build();

		notificationManager.notify(NOTIFICATION_ID_GRADES, notification);
	}

	public void buildFAQNotification(View view) {
		Notification notification = new Notification.Builder(MainActivity.this, CHANNEL_COURSE_FAQ)
				.setContentTitle("New answer by TA!")
				.setContentText("Ruibo answered your question!")
				.setSmallIcon(android.R.drawable.stat_notify_more)
				.setTimeoutAfter(5000)
				.build();

		notificationManager.notify(NOTIFICATION_ID_FAQ, notification);
	}

	private void initFAQChannel() {
		NotificationChannel faqChannel =
				new NotificationChannel(CHANNEL_COURSE_FAQ,
						"class FAQ channel",
						NotificationManager.IMPORTANCE_DEFAULT);
		faqChannel.setGroup(GROUP_COURSE_GRADES);
		notificationManager.createNotificationChannel(faqChannel);
	}

	private void initGradesChannel() {
		NotificationChannel gradesChannel =
				new NotificationChannel(CHANNEL_COURSE_GRADES,
						"class grades channel",
						NotificationManager.IMPORTANCE_HIGH);
		gradesChannel.setGroup(GROUP_COURSE_GRADES);
		gradesChannel.setShowBadge(true);
		notificationManager.createNotificationChannel(gradesChannel);
	}

	private void initNotesChannel() {
		NotificationChannel notesChannel =
				new NotificationChannel(CHANNEL_COURSE_NOTES,
						"class notes channel",
						NotificationManager.IMPORTANCE_LOW);
		notesChannel.setGroup(GROUP_COURSE_NOTES);
		notificationManager.createNotificationChannel(notesChannel);
	}
}
