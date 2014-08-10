package com.randi.dyned3.model;

import net.rim.device.api.ui.UiApplication;

import com.awan.dyned3.general.GeneralFunction;
import com.randi.dyned3.tools.ProgressListener;
import com.randi.dyned3.tools.ThreadManager;

/**
 * A thread class to download Audio and Avatar video files.
 * @author Randi Waranugraha
 *
 */
public class ContentLoader extends Thread implements ContentListener {
	
	private ProgressListener listener;
	private ThreadManager threadManager = ThreadManager.getInstance();
	private AudioDownload audioDownload = AudioDownload.getInstance();
	private AvatarDownload avatarDownload = AvatarDownload.getInstance();
	
	private int totalProgress;
	private int currentProgress;
	
	private static final String[] DIALOGUES_LESSON_ONE = {
		"NDE0301A.mp3", "NDE0301B.mp3", "NDE0301C.mp3"
	};
	
	private static final String[] QUESTIONS_LESSON_ONE = {
		"NDE0301B_Q1.mp3", "NDE0301B_Q2.mp3", "NDE0301B_Q3.mp3"
	};
	
	private static final String[] DIALOGUES = {
		"NDE0302A.mp3", "NDE0302B.mp3", "NDE0302C.mp3",

    	"NDE0303A.mp3", "NDE0303B.mp3", "NDE0303C.mp3", "NDE0303_BONUS.mp3",

    	"NDE0304A.mp3", "NDE0304B.mp3", "NDE0304C.mp3",

    	"NDE0305A.mp3", "NDE0305B.mp3", "NDE0305C.mp3",

    	"NDE0306A.mp3", "NDE0306B.mp3", "NDE0306C.mp3", "NDE0306_BONUS.mp3",

    	"NDE0307A.mp3", "NDE0307B.mp3", "NDE0307C.mp3",

    	"NDE0308A.mp3", "NDE0308B.mp3", "NDE0308C.mp3",

    	"NDE0309A.mp3", "NDE0309B.mp3", "NDE0309C.mp3", "NDE0309_BONUS.mp3",

    	"NDE0310A.mp3", "NDE0310B.mp3", "NDE0310C.mp3",

    	"NDE0311A.mp3", "NDE0311B.mp3", "NDE0311C.mp3", 

    	"NDE0312A.mp3", "NDE0312B.mp3", "NDE0312C.mp3", "NDE0312_BONUS.mp3"
	};
	
	private static final String[] QUESTIONS = {
		"NDE0302B_Q1.mp3",

    	"NDE0302B_Q2.mp3", "NDE0302B_Q3.mp3", "NDE0303B_Q1.mp3", "NDE0303B_Q2.mp3",

    	"NDE0303B_Q3.mp3", "NDE0304B_Q1.mp3", "NDE0304B_Q2.mp3", "NDE0304B_Q3.mp3",

    	"NDE0305B_Q1.mp3", "NDE0305B_Q2.mp3", "NDE0305B_Q3.mp3", "NDE0306B_Q1.mp3",

    	"NDE0306B_Q2.mp3", "NDE0306B_Q3.mp3", "NDE0307B_Q1.mp3", "NDE0307B_Q2.mp3",

    	"NDE0307B_Q3.mp3", "NDE0308B_Q1.mp3", "NDE0308B_Q2.mp3", "NDE0308B_Q3.mp3",

    	"NDE0309B_Q1.mp3", "NDE0309B_Q2.mp3", "NDE0309B_Q3.mp3", "NDE0310B_Q1.mp3",

    	"NDE0310B_Q2.mp3", "NDE0310B_Q3.mp3", "NDE0311B_Q1.mp3", "NDE0311B_Q2.mp3",

    	"NDE0311B_Q3.mp3", "NDE0312B_Q1.mp3", "NDE0312B_Q2.mp3", "NDE0312B_Q3.mp3"
	};

//	private static final String[] AVATARS = {
//		"DynEd1Done.3gp", "DynEd2BDone.3gp", "DynEd3ADone.3gp", "DynEd4Done.3gp", "DynEd5Done.3gp", 
//		"DynEd6ADone.3gp", "DynEd7ADone.3gp", 
//	};
	private static final String[] AVATARS = {
		"DynEd3.3gp", "DynEd4.3gp", "DynEd5.3gp", 
		"DynEd6.3gp", "DynEd7.3gp", 
	};

	/**
	 * Creates new object of ContentLoader
	 */
	public ContentLoader() {
	}

	/**
	 * Sets the ProgressListener to this object.
	 * @param listener 
	 */
	public void setListener(ProgressListener listener) {
		this.listener = listener;
	}
	
	public void run() {
		GeneralFunction.createDirectory(Resources.PATH);
		GeneralFunction.createDirectory(Resources.PATH_AUDIO);
		GeneralFunction.createDirectory(Resources.PATH_AUDIO_LEVEL);
		GeneralFunction.createDirectory(Resources.PATH_AUDIO_DIALOGUES);
		GeneralFunction.createDirectory(Resources.PATH_AUDIO_QUESTIONS);
		GeneralFunction.createDirectory(Resources.PATH_AVATAR);
		GeneralFunction.createDirectory(Resources.PATH_AVATAR_EN);

		if(listener != null) {
			listener.onPost("Preparing content..");
		}
		
		totalProgress = DIALOGUES_LESSON_ONE.length + QUESTIONS_LESSON_ONE.length;

		for(int i = 0; i < DIALOGUES_LESSON_ONE.length; i++){
			String dialog = DIALOGUES_LESSON_ONE[i];
			String downloadUrl = Resources.FILES_AUDIO_DIALOGUES + dialog;
			String saveLocation = Resources.PATH_AUDIO_DIALOGUES + dialog;

			DownloadAndSaveFile downloadAndSaveFile = new DownloadAndSaveFile(saveLocation, downloadUrl);
			downloadAndSaveFile.setContentListener(this);
			downloadAndSaveFile.setLabel(dialog);
			threadManager.register(downloadAndSaveFile);
		}

		for(int i = 0; i < QUESTIONS_LESSON_ONE.length; i++) {
			String question = QUESTIONS_LESSON_ONE[i];
			String downloadUrl = Resources.FILES_AUDIO_QUESTIONS + question;
			String saveLocation = Resources.PATH_AUDIO_QUESTIONS + question;

			DownloadAndSaveFile downloadAndSaveFile = new DownloadAndSaveFile(saveLocation, downloadUrl);
			downloadAndSaveFile.setContentListener(this);
			downloadAndSaveFile.setLabel(question);
			threadManager.register(downloadAndSaveFile);
		}

		for(int i = 0; i < DIALOGUES.length; i++) {
			audioDownload.addDialogs(DIALOGUES[i]);
		}

		for(int i = 0; i < QUESTIONS.length; i++) {
			audioDownload.addQuestions(QUESTIONS[i]);
		}
		
		for(int i = 0; i < AVATARS.length; i++) {
			avatarDownload.addAvatars(AVATARS[i]);
		}
		
		if(listener != null){
			listener.onPost("Get ready to download");
		}
		threadManager.start();
		super.run();
	}

	public void onFinishTask(String label) {
		currentProgress++;
		if(listener != null) {
			listener.onProgress(currentProgress, totalProgress);
		}
		if(currentProgress == totalProgress) {
			synchronized (UiApplication.getEventLock()) {
				UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
				audioDownload.start();
				avatarDownload.start();
			}
		}
	}

	public void onErrorTask(String label) {
		onFinishTask(label);
	}
}