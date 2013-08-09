package com.trinary.tcg

import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.awt.RenderingHints

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Graphics2D

class FileService {
	def grailsApplication
        
        def crop(def file, def x, def y, def width, def height, def finalWidth, def finalHeight) {
            println "file: ${file}}"
            println "x:    ${x}"
            println "y:    ${y}"
            println "w:    ${width}"
            println "h:    ${height}"
        
            // Get cropped image
            BufferedImage image = ImageIO.read(new File(file));
            BufferedImage croppedImage = image.getSubimage(x,  y, width, height)
            BufferedImage scaledImage = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_ARGB);
            String md5 = generateMD5(file)
            String filename = md5 + ".png"
            String path = grailsApplication.config.root + "art/" + filename

            // Scale cropped image back down to original size.
            Graphics2D g = scaledImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.drawImage(croppedImage, 0, 0, finalWidth, finalHeight, null)

            // Write cropped image
            try {
                ImageIO.write(scaledImage, "png", new File(path))
                new File(file).delete()
            } catch (IOException ex) {
                return null
            }
            
            return filename
        }
        
        def validateImage(def file, def width, def height) {
            BufferedImage image = ImageIO.read(new File(file))
            return image.getHeight() != height && image.getWidth()
        }
	
	def store(def file) {
		def path = null
		def thumbnailPath = null
		if (!file.empty) {
			def md5 = generateMD5(file)
			def extension = getExtension(file)
                        String filename = md5 + extension
			
			path = grailsApplication.config.root + "art/" + md5 + extension
			
			file.transferTo(new File(path))
                        return filename
		}
                
                return null
	}
	
	def writeImage(final String filename, OutputStream out) {
		def file = new File(filename)
		
		if (!file || file.size() == 0) {
			return false
		}
		
		out << file.getBytes()
		return true
	}
	
	def getExtension(CommonsMultipartFile file) {
		def filename = file.getOriginalFilename()
		
		if (filename.lastIndexOf('.')) {
			return filename[filename.lastIndexOf('.')..-1]
		}
		else {
			return ""
		}
	}
	
	def getExtension(File file) {
		def filename = file.name
		
		if (filename.lastIndexOf('.')) {
			return filename[filename.lastIndexOf('.')..-1]
		}
		else {
			return ""
		}
	}
        
        def getExtension(String filename) {
		if (filename.lastIndexOf('.')) {
			return filename[filename.lastIndexOf('.')..-1]
		}
		else {
			return ""
		}
	}
	
	def generateMD5(final file) {
		//I realize this isn't a hash
		return new Date().getTime()
	}
}