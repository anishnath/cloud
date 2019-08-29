<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Mysql service online</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Get a mysql database online">
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
            title : 'Your Data Base List',
            actions : {
                listAction : 'MYSQLServlet?action=list',
                createAction : 'MYSQLServlet?action=create',
                updateAction : 'MYSQLServlet?action=update',
                deleteAction : 'MYSQLServlet?action=delete'
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
                dbname : {
                    title : 'DB Name',
                    width : '10%',
                    list : true,
                    edit : false,
                    create : false
                },
                dbusername : {
                    title : 'DB User Name',
                    width : '10%',
                    list : true,
                    edit : false,
                    create : false
                },
                password : {
                    title : 'DB Password',
                    width : '10%',
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
                Timestamp : {
                    title : 'Time Stamp',
                    width : '20%',
                    list : true,
                    edit : false,
                    create : false
                },
            }
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

       <!-- Sidebar -->

<%@ include file="sidebar.jsp"%>

<!-- Sidebar Ends -->

    <!-- Page Content -->
    <div id="page-content-wrapper">

      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <button class="btn btn-primary" id="menu-toggle"> Menu</button>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

<!--         <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
            <li class="nav-item active">
              <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Dropdown
              </a>
              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">Something else here</a>
              </div>
            </li>
          </ul>
        </div> -->
      </nav>
      
      <h2 class="mt-4">MYSQL Service</h2>
      

      <div class="container-fluid">
      
       
                              
      
       <div id="dockerContainer"></div>
       
       <%
            
            String username1 = (String)session.getAttribute("user_name");
            if(username1!=null)
            {
            	
            %>
       
       <div class="embed-responsive embed-responsive-16by9">
  <iframe class="embed-responsive-item" src="https://mysql.0cloud0.com" allowfullscreen></iframe>
</div>

<%
            }
%>
       
			
			<%@ include file="addcomments.jsp"%>
			
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