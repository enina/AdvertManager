




		var so = new SWFObject("${pageContext.request.contextPath}/amMap/ammap.swf", "ammap", "600", "400", "8", "#FFFFFF");
                
                so.addVariable("path", "${pageContext.request.contextPath}/amMap/");
                so.addVariable('map_data','<map><areas><area title="UNITED KINGDOM" mc_name="GB" value="59778002" ></area></areas></map>');
                //so.addVariable("data_file", escape("${pageContext.request.contextPath}/amMap/ammap_data.xml"));
                so.addVariable("settings_file", escape("${pageContext.request.contextPath}/amMap/ammap_settings.xml"));		
                so.addVariable("preloader_color", "#999999");
                so.write("flashcontent");