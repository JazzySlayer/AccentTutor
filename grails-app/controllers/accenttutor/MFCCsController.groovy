package accenttutor

import com.accenttutor.Configuration
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItem
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class MFCCsController {

    @Secured(['permitAll'])
    def index() {

//        def audioFiles = params.recordedFile
//        AudioInputStream ais = audioFiles.getBytes()
//        if(ais){
//            println "Entered"
//        }
//        else{
//            println "not"
//        }ef data = request.getFile('recordedFile')

        FileItem fileItem = params.valueFName.getFileItem();
        DiskFileItem diskFileItem = (DiskFileItem) fileItem;
        String absPath = diskFileItem.getStoreLocation().getAbsolutePath();
        println absPath
        FeatureFileExtractor featureFileExtractor = new FeatureFileExtractor()
//        int result = featureFileExtractor.computeFeatures(params.fileName,"C:\\Users\\Sushant\\Downloads","C:\\Users\\Sushant\\Desktop")
//        FeatureFileExtractor featureFileExtractor1 = new FeatureFileExtractor()
//        featureFileExtractor1.computeFeatures("myRecording01","C:\\Users\\Sushant\\Downloads","C:\\Users\\Sushant\\Downloads")
        int result = featureFileExtractor.computeFeatures(absPath)       //tyo kA1 ko satta record
        if(result>2){
            return render([messageType:"success"] as JSON)
        }
        else{
            return render([messageType:"error"] as JSON)
        }
    }
    @Secured(['ROLE_ADMIN'])
    def configure(){
        def configuration = Configuration.findById(params.wordId)
        if (configuration == null){
            configuration = new Configuration()
            configuration.id=params.wordId
            println "null"
        }
//        println configuration.id
        render ([configuration:configuration] as JSON)
    }

    @Secured(['ROLE_ADMIN'])
    def saveConfigure(){
        println params.templateName
        println params.standardPronunciation
    }
}
