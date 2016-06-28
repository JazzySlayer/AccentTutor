<%--
  Created by IntelliJ IDEA.
  User: anijor
  Date: 6/27/2016
  Time: 9:55 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'home.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}" type="text/css">
    <script src="js/audiodisplay.js"></script>
    <script src="js/recorder.js"></script>
    <script src="js/main.js"></script>
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
    <div class="page-header">
        <h1>Accent Tutor</h1>
    </div>
    <div class="row">
        <div class="col-md-12">
        </div>
    </div>

    <div class="row">
        <div class="col-md-2">
        </div>
        <div class="col-md-8">
            <table class="table table-responsive">
                <tbody>
                <tr>
                    <td>Hello World</td>
                    <td>
                        <button type="button" class="btn btn-default btn1" data-toggle="modal" data-target="#myModal">
                            <span class="glyphicon glyphicon-record "></span>  record
                        </button>
                    </td>
                    </tr>
                <tr>
                    <td>Hello World   \!!!!</td>
                    <td>
                        <button type="button" class="btn btn-default btn1" data-toggle="modal" data-target="#myModal">
                            <span class="glyphicon glyphicon-record "></span>  record
                        </button>
                    </td>
                    </tr>
                <tr>
                    <td>Hello World ^#^</td>
                    <td>
                        <button type="button" class="btn btn-default btn1" data-toggle="modal" data-target="#myModal">
                            <span class="glyphicon glyphicon-record "></span>  record
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="col-md-2">


        </div>



    </div>
    <div class="row">


        <!-- Modal -->
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Modal Header</h4>
                    </div>

                        <div id="viz" class="modal-body">
                            <canvas id="analyser"></canvas><input type="button" value="Start & Stop" onclick="toggleRecording(this);">
                            <canvas id="wavedisplay"></canvas>  <a id="save" href="#"><input type="button" value="Export" ></a>
                        </div>
                    </div>
                    <div class="modal-footer">

                    </div>
                </div>

            </div>
        </div>

    </div>

</div>
</body>
</html>