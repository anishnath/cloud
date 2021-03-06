<!DOCTYPE html>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="k8.PlaygroundConstants"%>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="Online Terminal Mysql,PHP,Python,centos7,rhel7,rhel8,ubuntu 18.04,PHP7,nodejs,Ansible,archlinux,opensuse,ruby,lua,go,rust,scala,openldap,debian10,fedora,centos8">
  <meta name="keywords" content="Online Terminal Mysql,PHP,Python,centos7,rhel7,rhel8,ubuntu 18.04,PHP7,nodejs,Ansible,archlinux,opensuse,ruby,lua,go,rust,scala,openldap,debian10,fedora,centos8">
  <meta name="google-site-verification" content="bGqQHUWnhNwz3WOXE9hH6cprbhHfSI4q3mdWzLNHQSg" />
  <meta name="author" content="Anish Nath">
  
  <%@ include file="analytics.jsp"%>

  <title>Online Terminal Mysql,PHP,Python,centos7,rhel7,rhel8,ubuntu 18.04,PHP7,nodejs,Ansible,archlinux,opensuse,ruby,lua,go,rust,perl,scala,debian10,fedora,centos8</title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="css/simple-sidebar.css" rel="stylesheet">
  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  
  	<script type="text/javascript">
		$(document).ready(function() {


			$('#python3').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			$('#mariadb10').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			$('#rhel7').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			$('#centos7').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			$('#ubuntu').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			
			$('#rhel8').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			$('#php7fpm').click(function(event) {
				$('#form1').delay(200).submit()
			});
			

			$('#nodejs').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			$('#ansible').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			$('#archlinux').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			$('#opensuseleap').click(function(event) {
				$('#form1').delay(200).submit()
			});
			$('#ruby').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			$('#lua').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			$('#go').click(function(event) {
				$('#form1').delay(200).submit()
			});
			$('#rust').click(function(event) {
				$('#form1').delay(200).submit()
			});
			$('#perl').click(function(event) {
				$('#form1').delay(200).submit()
			});
			$('#scala').click(function(event) {
				$('#form1').delay(200).submit()
			});
			$('#java').click(function(event) {
				$('#form1').delay(200).submit()
			});
			$('#openldap').click(function(event) {
				$('#form1').delay(200).submit()
			});
			$('#debian').click(function(event) {
				$('#form1').delay(200).submit()
			});
			$('#fedora').click(function(event) {
				$('#form1').delay(200).submit()
			});
			$('#centos8').click(function(event) {
				$('#form1').delay(200).submit()
			});
			
			
			

			$('#form').submit(function(event) {
				//
				$('#msg').html('<p>Hang On tight We are building This will take approx 10-20 seconds')
				$('#output').html('<img src="images/712.GIF"> loading...');
				event.preventDefault();
				$.ajax({
					type : "POST",
					url : "playground", //this is my servlet

					data : $("#form").serialize(),
					success : function(msg) {
						$('#msg').empty();
						$('#output').empty();
						$('#python3').prop('disabled', false);
						$('#mariadb10').prop('disabled', false);
						$('#output').append(msg);

					}
				});
			});
			
			$('#form1').submit(function(event) {
				//
				$('#msg').html('<p>Hang On tight We are building This will take approx 10-20 seconds</p>')
				$('#output').html('<img class="img-fluid rounded" src="images/712.GIF"> loading...');
				event.preventDefault();

			
				
				$.ajax({
					type : "POST",
					url : "playground", //this is my servlet

					data : $("#form1").serialize(),
					success : function(msg) {
						$('#msg').empty();
						$('#msg').html('<p>Hang On tight We are still building Try Refreshing the page if Terminal Window doesnt open Automatically with 10-20 Seconds</p>')
						$('#python3').prop('disabled', false);
						$('#mariadb10').prop('disabled', false);
						//$('#output').append(msg);
						
						console.log(msg)
						
						var host=msg
						
						data = new FormData();
						
						
						$.ajax({
							<%
							
							String sessionid= request.getSession().getId();
							
							%>
							type : "GET",
							url : "poll", //this is my poll servlet
							data: {
								host: host,
								csrf_token: '<%=sessionid%>'
							},
							success : function(msg) {
								console.log(msg)
								
								if(msg==200)
									{
									
									console.log(host)
									
									//host='zvkqumuxdyawslvbjjmo.minikube'
									var html = '<div class="embed-responsive embed-responsive-16by9"><iframe class=embed-responsive-item src=https://'+host+'></iframe></div>';
									var newTab = '<a href=https://'+host+' target=_blank>Terminal is ready Now. Click here to Open in New Tab and share it With Others Realtime</a>';
									console.log(html)
									$('#msg').empty();
									$('#output').empty();
									$('#output').append(html)
									$('#msg').append(newTab)
									$('#loop').hide()
									}
								else{
									var html1='<p>Kindly refresh the Page or</p>';
									var html='<p><a href=https://'+host+' target=_blank>Click to Open in a new TAB</a></p>';
									console.log(html)
									console.log(html1)
									$('#msg').empty();
									$('#output').empty();
									$('#output').append(html)
									$('#msg').append(html1)
									$('#loop').show()
								}
								
							},
							
							
						});
						

					}
				});
			});
			
		});
	</script>
  

</head>

<body>

<%@ include file="navigation.jsp"%>



<div></div>
<hr>
  <div class="d-flex" id="wrapper">


<hr>
    <!-- Sidebar -->
<%@ include file="sidebar.jsp"%>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">



      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <button class="btn btn-primary" id="menu-toggle">Menu</button>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        

        
      </nav>

      <div class="container-fluid">
      
       <a href="https://twitter.com/anish2good?ref_src=twsrc%5Etfw" class="twitter-follow-button" data-show-count="false">Follow Me for Updates</a><script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        
        <div id="loading" style="display: none;">
			<img class="img-fluid rounded" src="images/712.GIF" alt="" />Loading!
		</div>
		
		<div class="sharethis-inline-share-buttons"></div>
        
        <%
        
        String sessionId = request.getSession().getId();
        
        %>
        
        <h2 class="mt-4">Launch your desire Terminal and share the screen terminal Realtime with Others</h2>

      
      <form class="form-horizontal-row" id="form1" method="POST">
	      <input type="hidden" name="csrf_token" id="csrf_token" value="<%=sessionId%>">

	      
		
		<div class="radio">
			<label>
				<input id="python3" type="radio" name="action" value="python3"><img class="img-fluid rounded" src="images/playground/python3.png"  alt="python3 terminal online">
				<input id="archlinux" type="radio" name="action" value="archlinux"><img class="img-fluid rounded" src="images/playground/archlinux.png"  alt="archlinux terminal online">
				<input id="centos7" type="radio" name="action" value="centos7"><img class="img-fluid rounded" src="images/playground/centos.png"  alt="centos7 terminal online">
				<input id="centos8" type="radio" name="action" value="centos8"><img class="img-fluid rounded" src="images/playground/centos8.png"  alt="centos8 vm online">
				<input id="fedora" type="radio" name="action" value="fedora"><img class="img-fluid rounded" src="images/playground/fedora.png"  alt="fedora30 terminal online">
				<input id="rhel7" type="radio" name="action" value="rhel7"><img class="img-fluid rounded" src="images/playground/rhel7.png"  alt="rhel7 terminal online">
				<input id="rhel8" type="radio" name="action" value="rhel8"><img class="img-fluid rounded" src="images/playground/rhel8.png"  alt="rhel8 terminal online">
				<input id="ubuntu" type="radio" name="action" value="ubuntu"><img class="img-fluid rounded" src="images/playground/ubuntu.png"  alt="ubuntu terminal online">
				<input id="debian" type="radio" name="action" value="debian"><img class="img-fluid rounded" src="images/playground/debian.png"  alt="debian10 terminal online">
				<input id="opensuseleap" type="radio" name="action" value="opensuseleap"><img class="img-fluid rounded" src="images/playground/opensuseleap.png"  alt="opensuseleap terminal online">
				<input id="php7fpm" type="radio" name="action" value="php7fpm"><img class="img-fluid rounded" src="images/playground/php7fpm.png"  alt="php7fpm terminal online">
				<input id="nodejs" type="radio" name="action" value="nodejs"><img class="img-fluid rounded" src="images/playground/nodejs.png"  alt="nodejs terminal online">
				<input id="ruby" type="radio" name="action" value="ruby"><img class="img-fluid rounded" src="images/playground/ruby.jpg"  alt="ruby terminal online">
				<input id="ansible" type="radio" name="action" value="ansible"><img class="img-fluid rounded" src="images/playground/ansible.png"  alt="ansible terminal online">
				<input id="lua" type="radio" name="action" value="lua"><img class="img-fluid rounded" src="images/playground/lua.png"  alt="lua terminal online">
				<input id="go" type="radio" name="action" value="go"><img class="img-fluid rounded" src="images/playground/go.png"  alt="go terminal online">
				<input id="rust" type="radio" name="action" value="go"><img class="img-fluid rounded" src="images/playground/rust.png"  alt="rust terminal online">
				<input id="perl" type="radio" name="action" value="perl"><img class="img-fluid rounded" src="images/playground/perl.png"  alt="perl terminal online">
				<input id="java" type="radio" name="action" value="scala"><img class="img-fluid rounded" src="images/playground/java.png"  alt="java terminal online">
				<input id="scala" type="radio" name="action" value="scala"><img class="img-fluid rounded" src="images/playground/scala.png"  alt="scala terminal online">
				<input id="openldap" type="radio" name="action" value="openldap"><img class="img-fluid rounded" src="images/playground/openldap.png"  alt="openldap terminal online">
				<input id="mariadb10" type="radio" name="action" value="mariadb10"><img class="img-fluid rounded" src="images/playground/mariadb.png"  alt="mariadb10 terminal online">
			</label>
		</div>
	      </form>
      
	      <div id="msg"></div>
	      <div id="output"></div>
	      
	      <div id="loop">
	      
	      
	      
	      <%
	      
	      Map<String,String> map = PlaygroundConstants.imageMap;
	      
	      Iterator it = map.entrySet().iterator();
	      
	      for (String key : map.keySet()) {
	    	  
	    	  //System.out.println("Key" + key);
	    	  String host = (String)request.getSession().getAttribute(key);
	    	  
	    	  if(host!=null)
	    	  {
	    		  
	    		  %>
	    		  
	    		  <div class="embed-responsive embed-responsive-4by3"><iframe class=embed-responsive-item src=https://<%=host %>></iframe></div>
	    		  
	    		  <%
	    		  
	    	  }
	    			  
	    			  
	    	  
	      }
	      
	      %>
	      
	      </div>
	      
	      
	      <%@ include file="addcomments.jsp"%>

	      
      </div>
    </div>
    <!-- /#page-content-wrapper -->

  </div>
  <!-- /#wrapper -->

  <!-- Bootstrap core JavaScript -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Menu Toggle Script -->
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>

</body>

</html>