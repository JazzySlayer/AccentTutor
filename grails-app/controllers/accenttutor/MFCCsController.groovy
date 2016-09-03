package accenttutor

import com.accenttutor.Configuration
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItem
import org.codehaus.groovy.grails.web.context.ServletContextHolder
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

        FileItem fileItem = params.valueFName.getFileItem()
        DiskFileItem diskFileItem = (DiskFileItem) fileItem
        String absPath = diskFileItem.getStoreLocation().getAbsolutePath()
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
        /*println params.templateName1
        println params.templateName2
        println params.templateName3
        println params.standardPronunciation

        def configuration = new Configuration()
        configuration.templateName=""
        configuration.standardPronunciation=""

        FileItem fileItem = params.templateName.getFileItem()
        DiskFileItem diskFileItem = (DiskFileItem) fileItem
        String absPath = diskFileItem.getStoreLocation().getAbsolutePath()
        println absPath

        fileItem = params.standardPronunciation.getFileItem()
        diskFileItem = (DiskFileItem) fileItem
        absPath = diskFileItem.getStoreLocation().getAbsolutePath()
        println absPath

        fileItem = params.templateName1.getFileItem()
        diskFileItem = (DiskFileItem) fileItem
        absPath = diskFileItem.getStoreLocation().getAbsolutePath()
        println absPath

        fileItem = params.templateName2.getFileItem()
        diskFileItem = (DiskFileItem) fileItem
        absPath = diskFileItem.getStoreLocation().getAbsolutePath()
        println absPath

        fileItem = params.templateName3.getFileItem()
        diskFileItem = (DiskFileItem) fileItem
        absPath = diskFileItem.getStoreLocation().getAbsolutePath()
        println absPath*/
        /*def servletContext = ServletContextHolder.servletContext
        def storagePath = servletContext.getRealPath("templates")
        def name = params.templateName.getOriginalFilename();

        def storagePathDirectory = new File(storagePath)
        if (!storagePathDirectory.exists()) {
            storagePathDirectory.mkdirs()
        }
        println storagePath

        params.templateName.transferTO(new File(storagePath+"/"+name))*/

        return render([messageType:"success"] as JSON)

    }
}
