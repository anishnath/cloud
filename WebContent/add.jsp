<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Deploy Docker Image online </title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Deploy docker image free hosting, docker playground kubernetes Playground">
<meta name="author" content="Anish Nath">
<link href="css/simple-sidebar.css" rel="stylesheet">

<%@ include file="analytics.jsp"%>
<!-- Include one of jTable styles. -->


<!-- <link href="css/jtable.css" rel="stylesheet" type="text/css" /> -->
<link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<!-- Include jTable script file. -->
<script src="js/jquery-1.8.2.js" type="text/javascript"></script>
<!--  <script src="http://code.jquery.com/jquery-1.9.1.js"></script> -->
<script src="js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
<script src="js/jquery.jtable.js" type="text/javascript"></script>

 <!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<%@ include file="style.jsp"%>

 

<script type="text/javascript">
    $(document).ready(function() {
        
    	$('#dockerContainer').jtable({
            title : 'Your Deployment List',
            actions : {
                listAction : 'deploy?action=list',
                createAction : 'deploy?action=create',
                updateAction : 'deploy?action=update',
                deleteAction : 'deploy?action=delete'
            },
            fields : {
                id : {
                    title : 'id',
                    width : '10%',
                    key : true,
                    list : true,
                    edit : false,
                    create : false
                },
                docker_image : {
                    title : 'Docker Image',
                    width : '10%',
                    edit : true
                },
                expose_port : {
                    title : 'Container Port',
                    width : '10%',
                    input: function (data) {
                        if (data.record) {
                            return '<input type="text" name="expose_port" style="width:200px" value="' + data.record.expose_port + '" />';
                        } else {
                            return '<input type="text" name="expose_port" style="width:200px" placeholder="8080" />';
                        }
                    },
                    edit : true
                },
                container_command : {
                    title : 'Container Command',
                    width : '10%',
                    input: function (data) {
                        if (data.record) {
                            return '<textarea name="container_command">'+ data.record.container_command +'</textarea>';
                        } else {
                            return '<textarea placeholder="/bin/sh" name="container_command"></textarea>';
                        }
                    },
                    edit : false
                },
                args : {
                    title : 'ARGS(Comma Seperated)',
                    width : '10%',
                    input: function (data) {
                        if (data.record) {
                            return '<textarea name="args">'+ data.record.args +'</textarea>';
                        } else {
                            return '<textarea placeholder="-c,while true; do echo hello; sleep 10;done" name="args"></textarea>';
                        }
                    },
                    edit : false
                },
                enviroment_vars : {
                    title : 'ENV VARS',
                    width : '10%',
                    input: function (data) {
                        if (data.record) {
                            return '<textarea name="container_args">'+ data.record.enviroment_vars +'</textarea>';
                        } else {
                            return '<textarea placeholder="A=A\nB=B" name="enviroment_vars"></textarea>';
                        }
                    },
                    edit : false
                },
                expose_url : {
                    title : 'Exposed URL',
                    width : '30%',
                    list : true,
                    edit : false,
                    create : false
                },
                Timestamp : {
                    title : 'Time Stamp',
                    width : '20%',
                    list : true,
                    edit : false,
                    create : false
                },
                status : {
                    title : 'STATUS',
                    width : '10%',
                    list : true,
                    edit : false,
                    create : false
                },
            }
        });
    	
    	
		
		

		$('#form1').submit(function(event) {
			//
			$('#msg').html('<p>Hang On tight We are building This will take approx 10-20 seconds')
			$('#output').html('<img src="images/712.GIF"> loading...');
			event.preventDefault();
			$.ajax({
				type : "POST",
				url : "deploy", //this is my servlet

				data : $("#form1").serialize(),
				success : function(msg) {
					$('#dockerContainer').jtable('load');
					$('#output').empty();
					$('#msg').empty();

				}
			});
		});
    	
    	
    	
        $('#dockerContainer').jtable('load');
    });
</script>


</head>
<body>





<%@ include file="navigation.jsp"%>

<div></div>
<hr>


 <%
                              
                              String error = (String)request.getAttribute("error");
                              if(error!=null)
                              {
                            	  %>
                              
                              <p><font color="red"><%=error %></font></p>
                              
                              <% } %>
                              


  <div class="d-flex" id="wrapper">


    <!-- Sidebar -->

<%@ include file="sidebar.jsp"%>

<!-- Sidebar Ends -->


    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">

      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <button class="btn btn-primary" id="menu-toggle"> Menu</button>


      </nav>
      
      <div class="sharethis-inline-share-buttons"></div>
      
      <h2 class="mt-4">Container Service</h2>
      

      <div class="container-fluid">
      
       
                              
      
       <div id="dockerContainer"></div>
       
       
       <hr>
       <h4>Launch Stacks </h4>
       
       <%
        
        String sessionId = request.getSession().getId();
        
        %>
        
          <div id="msg"></div>
	      <div id="output"></div>
       
          <form class="form-horizontal-row" action="deploy" id="form1" method="post">
	      <input type="hidden" name="csrf_token" id="csrf_token" value="<%=sessionId%>">

	     
		<div class="radio">
			<label>
				<input id="mysql" type="radio" name="action" value="mysql"><img class="img-fluid rounded" src="images/playground/mysql.png"  alt="mysql">
				<input id="wordpress" type="radio" name="action" value="wordpress"><img class="img-fluid rounded" src="images/playground/wordpress.png"  alt="mysql">
				
				
				<input class="btn btn-primary" type="submit" value="Submit">
			</label>
		</div>
	      </form>
       
       
       <hr>
       <p> When you add a new deployment please refresh the page to get into the DEPLOYED STATE. This state refer your HTTP application is ready to serve the traffic </p>
       <p>
       <img class="img-fluid rounded" height="800" src="images/deploy.png"  alt="Referefce ">
       </img>
       </p>>
       
       <%@ include file="addcomments.jsp"%>
       
       <div>
       
       <hr>
       <h3 class="code-line" data-line-start=0 data-line-end=1 ><a id="FAQ_0"></a>FAQ</h3>
<blockquote>
<p class="has-line-data" data-line-start="2" data-line-end="4">Q:What is Docker image  ?<br>
A Valid Docer image which is hosted in any Container Registry for example</p>
</blockquote>
<pre><code>    tomcat
    nginx
    gcr.io/your-project-id/your-namespace/your-image:latest
</code></pre>
<blockquote>
<p class="has-line-data" data-line-start="9" data-line-end="11">Q: What is Container Port ?<br>
A: A Service port which Docker Image expose, for example nginx default expose port is 80, tomcat default expose port is 8080</p>
</blockquote>
<blockquote>
<p class="has-line-data" data-line-start="12" data-line-end="14">Q: Can i deploy other Docker Images which is not exposed by any port ?<br>
A: Yes only the Service will not get exposed, you can still able to execute commands on those docker images</p>
</blockquote>
<blockquote>
<p class="has-line-data" data-line-start="15" data-line-end="17">Q: Is this paid Service  ?<br>
A: No</p>
</blockquote>
<blockquote>
<p class="has-line-data" data-line-start="18" data-line-end="20">Q: Is TLS Supported ?<br>
A: Working on it</p>
</blockquote>
<blockquote>
<p class="has-line-data" data-line-start="21" data-line-end="23">Q: How long the Service will be deployed ?<br>
A: This is a playground and i'm the only maintainer who is paying the bill so every day the service will purged and you need to re-deploy it</p>
</blockquote>
<blockquote>
<p class="has-line-data" data-line-start="24" data-line-end="26">Q:  How Many Docker images you can deploy ?<br>
A: Every User can deploy MAX 6 docker images or you can reach out to me if you need more docker images</p>
</blockquote>
<blockquote>
<p class="has-line-data" data-line-start="27" data-line-end="29">Q: Does it support stateful Applications<br>
A: Working on it ? for now NO</p>
</blockquote>
<blockquote>
<p class="has-line-data" data-line-start="30" data-line-end="32">Q: is Custom DNS Supported<br>
A: YES, you need to contact me for the same</p>
</blockquote>
       </div>
			
      </div>
    </div>
    <!-- /#page-content-wrapper -->

  </div>
  <!-- /#wrapper -->

  <!-- Bootstrap core JavaScript -->
 
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
 <!--  <script src="http://code.jquery.com/jquery-1.9.1.js"></script> -->

  <!-- Menu Toggle Script -->
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>

		

		
	

	





<div class="lefttop"> </div>



</div>

</body>
</html>