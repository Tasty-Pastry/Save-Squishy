/* Variable Dictionary:
 * 
 * clip: Used to reference the Clip interface
 * 
 */


package audio;

// Imports
import javax.sound.sampled.*;

public class AudioPlayer {

	// Variables
	public Clip clip;
	
	// Constructor
	public AudioPlayer(String s) {
		
		try {
			
			// Loads the audio in 
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
			
			AudioFormat baseFormat = ais.getFormat(); // Obtains the audio format of the sound data in this audio input stream.
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false); // Creates a new AudioPlayer with the decoded audio
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais); // Obtains an audio input stream of the indicated format, by converting the provided audio input stream.
			clip = AudioSystem.getClip(); // Obtains a clip that can be used for playing back an audio file or an audio stream.
			clip.open(dais); // Opens the clip with the format and audio data present in the provided audio input stream. 
			
		} catch (Exception e) {
			
			 e.printStackTrace(); // Prints errors
			
		}
		
	}
	
	public void play() {
		
		// Returns if there is no clip
		if (clip == null) {
			
			return;
			
		}
		
		// Plays clip
		stop();
		clip.setFramePosition(0);
		clip.start();
		
	}
	
	public void stop() {
		
		// Stops the clip
		if (clip.isRunning()) {
			
			clip.stop();
			
		}
		
	}
	
	public void close() {
		
		// Frees system memory
		stop();
		clip.close();
		
	}
	
}
