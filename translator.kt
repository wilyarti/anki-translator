// Imports the Google Cloud client library
import com.google.cloud.texttospeech.v1.*
import java.io.FileOutputStream


fun main(args: Array<String>) {
    
    fun readFileLineByLineUsingForEachLine(fileName: String)
            = File(fileName).forEachLine { process(it) }
    readFileLineByLineUsingForEachLine(args[0])
    
    listvoices()
}
fun process(string: String) {
    var words = string.split(',')
    println("${words}")
    translateWord(words[2], "${words[0]}.mp3")


}
fun translateWord(word: String, fileName: String) {
    // Instantiates a client
    TextToSpeechClient.create().use { textToSpeechClient ->
        // Set the text input to be synthesized
        val input = SynthesisInput.newBuilder()
                .setText(word)
                .build()

        // Build the voice request, select the language code ("en-US") and the ssml voice gender
        // ("neutral")
        val voice = VoiceSelectionParams.newBuilder()
                .setLanguageCode("ko-KR")
                .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                .build()

        // Select the type of audio file you want returned
        val audioConfig = AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.MP3)
                .build()

        // Perform the text-to-speech request on the text input with the selected voice parameters and
        // audio file type
        val response = textToSpeechClient.synthesizeSpeech(input, voice,
                audioConfig)

        // Get the audio contents from the response
        val audioContents = response.getAudioContent()

        // Write the response to the output file.
        FileOutputStream(fileName).use { out ->
            out.write(audioContents.toByteArray())
            println("Audio content written to file \"${fileName} \"")
        }
    }
}
fun listvoices() {
    TextToSpeechClient.create().use { textToSpeechClient ->
        // Builds the text to speech list voices request
        val request = ListVoicesRequest.getDefaultInstance()

        // Performs the list voices request
        val response = textToSpeechClient.listVoices(request)
        val voices = response.voicesList

        for (voice in voices) {
            // Display the voice's name. Example: tpc-vocoded
            System.out.format("Name: %s\n", voice.name)

            // Display the supported language codes for this voice. Example: "en-us"
            val languageCodes = voice.languageCodesList.asByteStringList()
            for (languageCode in languageCodes) {
                System.out.format("Supported Language: %s\n", languageCode.toStringUtf8())
            }

            // Display the SSML Voice Gender
            System.out.format("SSML Voice Gender: %s\n", voice.ssmlGender)

            // Display the natural sample rate hertz for this voice. Example: 24000
            System.out.format("Natural Sample Rate Hertz: %s\n\n",
                    voice.naturalSampleRateHertz)
        }
    }
}
