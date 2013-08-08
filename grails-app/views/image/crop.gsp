<html>
    <head>
        <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script>
            $(function() {
                $("#crop").click(function () {
                    var json = $("#crop-applet").get(0).getCropJson();
                    var obj = jQuery.parseJSON(json);
                    console.log("X: " + obj['x']);
                    console.log("Y: " + obj['y']);
                    console.log("W: " + obj['width']);
                    console.log("H: " + obj['height']);
                    
                    $.post({
                        url: "http://tcg-generator.no-ip.org/tcg-db/image/crop/${id}",
                        data: obj,
                        success: function(data) {
                            log.console("SUCCESS: " + data);
                        }
                    });
                });
                $("#zoom-in").click(function () {
                    var scale = $("#crop-applet").get(0).zoomIn();
                    console.log("SCALE: " + scale);
                });
                $("#zoom-out").click(function () {
                    var scale = $("#crop-applet").get(0).zoomOut();
                    console.log("SCALE: " + scale);
                });

                var imageDataJson = $("#crop-applet").get(0).getImageDataJson();
                var imageData = jQuery.parseJSON(imageDataJson);
                console.log("W: " + imageData['width']);
                console.log("H: " + imageData['height']);
                $("#crop-applet").css("width", imageData['width']);
                $("#crop-applet").css("height", imageData['height']);
            });
        </script>
    </head>
    <body>
        <applet id="crop-applet" code="com.trinary.imageedit.ImageEditApplet.class" codebase="applets" archive="ImageEditApplet.jar" style="border: 1px solid black;" width="100%" height="100%">
            <param name="image-file" value="http://tcg-generator.no-ip.org/tcg-lib/image/crop/${id}" />
            <param name="crop-width" value="327" />
            <param name="crop-height" value="477" />
        </applet><br/>
        <button id="crop">Crop</button>
        <button id="zoom-in">+</button>
        <button id="zoom-out">-</button>
    </body>
</html>
