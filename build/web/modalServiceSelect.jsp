<%-- 
    Document   : modalServiceSelect
    Created on : Jun 28, 2015, 12:12:48 PM
    Author     : kbuck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <a id="modal-185387" href="#modal-service-container" role="button" class="btn" data-toggle="modal">Select Services to add:</a>
                    <div class="modal fade" id="modal-service-container" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="myModalLabel">
                                        Select Services to add:
                                    </h4>
				</div>
				<div class="modal-body">
                                    <div class="form-group">
                                        <label for="service-search" class="col-lg-2 control-label">Services: </label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="service-search" type="text" placeholder="Type the service to add:">
                                        </div>
                                    </div>
				</div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="added">Services added:</label>
                                        <ul id="added"></ul>
                                    </div>
                                </div>
				<div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    <button id="serv-select-submit" type="button" class="btn btn-primary" data-dismiss="modal">Save changes</button>
				</div>
                            </div>		
                        </div>		
                    </div>
		</div>
            </div>
        </div>
    </body>
</html>
