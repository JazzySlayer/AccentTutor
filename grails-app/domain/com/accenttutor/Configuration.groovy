package com.accenttutor

/**
 * Created by anons on 8/28/16.
 */
class Configuration {
    String templateName
    String standardPronunciation

    static constraints = {
        templateName blank: false
        standardPronunciation blank: false
    }
}
