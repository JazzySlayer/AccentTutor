package accenttutor
import com.accenttutor.Configuration
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItem
import org.codehaus.groovy.grails.web.context.ServletContextHolder
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
        def conf = Configuration.findById(params.wordId)
        println params.wordId
        FileItem fileItem = params.valueFName.getFileItem()
        DiskFileItem diskFileItem = (DiskFileItem) fileItem
        String absPath = diskFileItem.getStoreLocation().getAbsolutePath()
        println absPath
        FeatureFileExtractor featureFileExtractor = new FeatureFileExtractor()
//        int result = featureFileExtractor.computeFeatures(params.fileName,"C:\\Users\\Sushant\\Downloads","C:\\Users\\Sushant\\Desktop")
//        FeatureFileExtractor featureFileExtractor1 = new FeatureFileExtractor()
//        featureFileExtractor1.computeFeatures("myRecording01","C:\\Users\\Sushant\\Downloads","C:\\Users\\Sushant\\Downloads")
        def result = featureFileExtractor.computeFeatures(absPath,conf)       //tyo kA1 ko satta record
        def success = false;
        def size = result.size()
        if (result.get(size-1)==(1.0)){
            success = true
        }
        else if (result.get(size-1)==(0.0)){
            success = false;
        }
        result.remove(size-1)
        println result
        if(success) {
            render(template: 'ajaxTemplate', model:[messageType: 'Success', result: result])
        }
        else{
            render(template: 'ajaxTemplate',model: [messageType:"Error", result: result])
        }
    }

    @Secured(['permitAll'])
    def initiate(){
        def conf = Configuration.findById(1)
        def conf1 = Configuration.findById(2)
        def standPronunciation = ""
        def standPronunciation1 = ""

        if (conf==null||conf1==null){
            return render([messageType:"fail"] as JSON)
        }
        else {
            standPronunciation = conf.standardPronunciation
            standPronunciation1 = conf1.standardPronunciation
            return render([messageType:"success",pronun1:standPronunciation,pronun2:standPronunciation1] as JSON)
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
        else{
            println configuration
        }

//        println configuration.id
        render ([configuration:configuration] as JSON)
    }

    @Secured(['ROLE_ADMIN'])
    def saveConfigure(){
        def servletContext = ServletContextHolder.servletContext
        def storagePath = servletContext.getRealPath( "templates" )
        def storagePathDirectory = new File(storagePath)

        if (!storagePathDirectory.exists()) {
            storagePathDirectory.mkdirs()
        }
        Transfer(params.templateName,storagePath)
        Transfer(params.templateName1,storagePath)
        Transfer(params.templateName2,storagePath)
        Transfer(params.templateName3,storagePath)
        Transfer(params.standardPronunciation,storagePath)

        def configuration = Configuration.findById(params.wordId)
        if (configuration == null){
            configuration = new Configuration()
        }
        def name = params.templateName.getOriginalFilename().tokenize('.').first()
        name = name.substring(0,name.length()-1)
        configuration.templateName = storagePath + "/" + name
        configuration.standardPronunciation = params.standardPronunciation.getOriginalFilename()

        println configuration.templateName
        println configuration.standardPronunciation
        if (configuration.save(flush: true,failOnError: true)){
            return render([messageType:"SaveSuccess"] as JSON)
        }
        else {
            return render([messageType:"SaveError"] as JSON)

        }

    }

    def Transfer(CommonsMultipartFile file, def storagePath){
        def name = file.getOriginalFilename()
        file.transferTo(new File(storagePath+"/"+name))
    }
}
