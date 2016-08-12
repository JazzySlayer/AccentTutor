package accenttutor

class MFCCController {


    def index() {
//        def audioFiles = params.recordedFile
//        AudioInputStream ais = audioFiles.getBytes()
//        if(ais){
//            println "Entered"
//        }
//        else{
//            println "not"
//        }ef data = request.getFile('recordedFile')



        FeatureFileExtractor featureFileExtractor = new FeatureFileExtractor()
        featureFileExtractor.computeFeatures("namaste","C:\\Users\\Sushant\\Downloads","C:\\Users\\Sushant\\Desktop")       //tyo kA1 ko satta record
//        FeatureFileExtractor featureFileExtractor1 = new FeatureFileExtractor()
//        featureFileExtractor1.computeFeatures("myRecording01","C:\\Users\\Sushant\\Downloads","C:\\Users\\Sushant\\Downloads")

        render(view: '../home')
    }
}
