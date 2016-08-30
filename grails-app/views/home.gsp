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
    <link rel="stylesheet" href="${resource(dir: 'fonts', file: 'glyphicons-halflings-regular.ttf')}" type="text/css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <script src="js/audiodisplay.js"></script>
    <script src="js/recorder.js"></script>
    <script src="js/main.js"></script>
     <script src="js/jquery.js"></script>
    <script src="js/soundmanager2.js"></script>
    <asset:javascript src="noty/packaged/jquery.noty.packaged.min.js"/>
    <script src="js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function(){
            $("#editModal").modal("hide");
            $("#compareModal").modal("hide");
        });

//        var ownfilename="myrecording00";
        function showCompareModal(){
            $("#compareModal").modal("show");
            console.log("-----------------"+$("#fileName").val())
        }
        soundManager.setup({
            url: 'mediaOfSounds/',
            flashVersion: 9, // optional: shiny features (default = 8)
            // optional: ignore Flash where possible, use 100% HTML5 mode
            // preferFlash: false,
            onready: function() {
                // Ready to use; soundManager.createSound() etc. can now be called.
            }
        });
       function showNoty(type,message){
            var n = noty({
                layout: 'top',
                theme: 'relax',
                type: type,
                text: message,
                animation: {
                    open: {height: 'toggle'},
                    close: {height: 'toggle'},
                    easing: 'swing', // easing
                    speed: 500
                },
                timeout: 1000
            })
        }

    </script>
</head>

<body>
<div class="jumbotron head">
    <h1 class="text-center">Accent Tutor</h1>
</div>
<div class="container-fluid">

    %{--<div class="row">
        <div class="col-md-12 nav-wrapper">
            <div id="navbar">
                <ul class="nav navbar-nav navbar-centre">
                    <sec:ifAllGranted roles="ROLE_ADMIN">
                        <li><g:link controller="MFCCs" action="configure"> Setting</g:link></li>
                    </sec:ifAllGranted>
                    --}%%{--<li><g:loginControl /></li>--}%%{--
                </ul>
            </div>
        </div>
    </div>--}%
    <g:render template="configure"/>
    <g:render template="compare"/>
    %{--<input type="hidden" id="fileName" value="">--}%
    <div class="row">
        <div class="col-md-2">
        </div>
        <div class="col-md-8">
            <table class="table table-responsive">
                <tbody>
                <tr>
                    <td>Namaste</td>
                    <td>
                        <a href="mediaOfSounds/NN2.wav">
                            <button type="button" class="btn btn-default btn1">
                                <span class="glyphicon glyphicon-play-circle"></span>  Play
                            </button>
                        </a>
                    </td>
                    <td>
                        <button type="button" class="btn btn-default btn1" onclick="showCompareModal();">
                            <span class="glyphicon glyphicon-align-justify"></span>  Compare
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-default btn1" data-toggle="modal" data-target="#myModal" onclick='changeName("NN",false);'>
                            <span class="glyphicon glyphicon-record "></span>  Record
                        </button>
                    </td>
                    <sec:ifAllGranted roles="ROLE_ADMIN">
                        <td><button type="button" class="btn btn-default btn1" onclick="EditDetails('1')" ><span class="glyphicon glyphicon-edit fa-5x"> Edit</span></button></td>
                    </sec:ifAllGranted>
                </tr>
                <tr>
                    <td>Dhanyabaad</td>
                    <td>
                        <a href="mediaOfSounds/dd3.wav">
                            <button type="button" class="btn btn-default btn1">
                                <span class="glyphicon glyphicon-play-circle"></span>  Play
                            </button>
                        </a>
                    </td>
                    <td>
                        <button type="button" class="btn btn-default btn1" onclick="showCompareModal();">
                            <span class="glyphicon glyphicon-align-justify"></span>  Compare
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-default btn1" data-toggle="modal" data-target="#myModal" onclick='changeName("dd",false)'>
                            <span class="glyphicon glyphicon-record"></span>  Record
                        </button>
                    </td>
                    <sec:ifAllGranted roles="ROLE_ADMIN">
                        <td><button type="button" class="btn btn-default btn1" onclick="EditDetails('2')" ><span class="glyphicon glyphicon-edit fa-5x"> Edit</span></button></td>
                    </sec:ifAllGranted>
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
                        <h4 class="modal-title">Record</h4>
                    </div>

                        <div id="viz" class="modal-body">
                            <canvas id="analyser"></canvas><input type="button" value="Start & Stop" class="button" onclick="toggleRecording(this);">
                            <canvas id="wavedisplay"></canvas>  <a id="save" href="#"><input type="button" class="button" value="Export" ></a>
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