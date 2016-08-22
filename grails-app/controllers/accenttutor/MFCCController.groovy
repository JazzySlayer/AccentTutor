package accenttutor

import grails.converters.JSON

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
        int result = featureFileExtractor.computeFeatures(params.fileName,"C:\\Users\\Sushant\\Downloads","C:\\Users\\Sushant\\Desktop")
//        FeatureFileExtractor featureFileExtractor1 = new FeatureFileExtractor()
//        featureFileExtractor1.computeFeatures("myRecording01","C:\\Users\\Sushant\\Downloads","C:\\Users\\Sushant\\Downloads")
        println result
        if(result>2){
            println "enteres!!!!!!!!!!" + result
            return render([messageType:"success"] as JSON)
        }
        else{
            return render([messageType:"error"] as JSON)
        }
    }
}
