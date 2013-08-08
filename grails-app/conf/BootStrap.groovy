import com.tcg.generator.config.ConfigHolder
import org.codehaus.groovy.grails.commons.GrailsApplication

class BootStrap {
    GrailsApplication grailsApplication

    def init = { servletContext ->
        ConfigHolder.setConfig("rootDirectory", grailsApplication.config.root)
    }
    def destroy = {
    }
}
