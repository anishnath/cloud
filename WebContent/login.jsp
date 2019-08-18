<!DOCTYPE html>
<%@page import="org.apache.commons.codec.binary.Hex"%>
<%@page import="java.security.MessageDigest"%>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

<%@ include file="analytics.jsp"%>
  <title>Login or Register</title>
  
  <link href="css/simple-sidebar.css" rel="stylesheet">

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
<link rel="stylesheet" href="./style.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>

<style>
/* Shared */
.loginBtn {
  box-sizing: border-box;
  position: relative;
  /* width: 13em;  - apply for fixed size */
  margin: 0.2em;
  padding: 0 15px 0 46px;
  border: none;
  text-align: left;
  line-height: 34px;
  white-space: nowrap;
  border-radius: 0.2em;
  font-size: 16px;
  color: #FFF;
}
.loginBtn:before {
  content: "";
  box-sizing: border-box;
  position: absolute;
  top: 0;
  left: 0;
  width: 34px;
  height: 100%;
}
.loginBtn:focus {
  outline: none;
}
.loginBtn:active {
  box-shadow: inset 0 0 0 32px rgba(0,0,0,0.1);
}


/* Facebook */
.loginBtn--facebook {
  background-color: #4C69BA;
  background-image: linear-gradient(#4C69BA, #3B55A0);
  /*font-family: "Helvetica neue", Helvetica Neue, Helvetica, Arial, sans-serif;*/
  text-shadow: 0 -1px 0 #354C8C;
}
.loginBtn--facebook:before {
  border-right: #364e92 1px solid;
  background: url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/14082/icon_facebook.png') 6px 6px no-repeat;
}
.loginBtn--facebook:hover,
.loginBtn--facebook:focus {
  background-color: #5B7BD5;
  background-image: linear-gradient(#5B7BD5, #4864B1);
}


/* Google */
.loginBtn--google {
  /*font-family: "Roboto", Roboto, arial, sans-serif;*/
  background: #DD4B39;
}
.loginBtn--google:before {
  border-right: #BB3F30 1px solid;
  background: url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/14082/icon_google.png') 6px 6px no-repeat;
}
.loginBtn--google:hover,
.loginBtn--google:focus {
  background: #E74B37;
}
</style>

</head>





<body>

<%@ include file="navigation.jsp"%>

    <div class="container">
        <div class="row">
			<div class="col-md-5 mx-auto">
			<div id="first">
			<hr>
			<hr><hr>
				<div class="myform form ">
					 <div class="logo mb-3">
						 <div class="col-md-12 text-center">
							<h1>Login With</h1>
						 </div>
						<!--  <button class="loginBtn loginBtn--facebook">
  Login with Facebook
</button> -->

<form action="login" method="post" name="login">

<input type="hidden" name="provider"  class="form-control" id="provider" aria-describedby="provider" value="google">
<button class="loginBtn loginBtn--google">
  Login with Google
</button>
</form>
					</div>
					
					<%
					
						String system=(String) request.getParameter("admin");
					
						if(system!=null && system.length()>0)
						{
						MessageDigest md = MessageDigest.getInstance("SHA-256");
						md.update(system.getBytes());
						
						byte[] digest = md.digest();
						
						String pass = Hex.encodeHexString(digest);
						
						if("8205d8ff6b0433adf52ac63a0eb2dc8e27eb6b9ab309b23fd16935207122fc84".equalsIgnoreCase(pass))
						{
						
					%>
					
                   <form action="login" method="post" name="login">
                   <input type="hidden" name="provider"  class="form-control" id="provider" aria-describedby="provider" value="system">
                           <div class="form-group">
                              <label for="exampleInputEmail1">Email address</label>
                              <input type="email" name="email"  class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter email">
                           </div>
                           <div class="form-group">
                              <label for="exampleInputEmail1">Password</label>
                              <input type="password" name="password" id="password"  class="form-control" aria-describedby="emailHelp" placeholder="Enter Password">
                           </div>
                           <div class="form-group">
                              <p class="text-center">By signing up you accept our <a href="#">Terms Of Use</a></p>
                           </div>
                           <div class="col-md-12 text-center ">
                              <button type="submit" class=" btn btn-block mybtn btn-primary tx-tfm">Login</button>
                           </div>
                           <div class="form-group">
                              <p class="text-center">Don't have account? <a href="#" id="signup">Sign up here</a></p>
                           </div>
                        </form>
                        
                        
                 
				</div>
			</div>
			
			<%
                              
                              String error = (String)request.getAttribute("error");
                              if(error!=null)
                              {
                            	  %>
                              
                              <p><font color="red"><%=error %></font></p>
                              
                              <% } %>
			
			  <div id="second">
			      <div class="myform form ">
                        <div class="logo mb-3">
                           <div class="col-md-12 text-center">
                              <h1 >Signup</h1>
                              
                              
                              
                              
                              
                           </div>
                        </div>
                        
                       
                        
                        <form action="RegisterServlet" name="registration" method="POST">
                           <div class="form-group">
                              <label for="exampleInputEmail1">Email address</label>
                              <input type="email" name="email"  class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter email">
                           </div>
                           <div class="form-group">
                              <label for="exampleInputEmail1">Password</label>
                              <input type="password" name="password" id="password"  class="form-control" aria-describedby="emailHelp" placeholder="Enter Password">
                           </div>
                           <div class="col-md-12 text-center mb-3">
                              <button type="submit" class=" btn btn-block mybtn btn-primary tx-tfm">Get Started</button>
                           </div>
                            </div>
                        </form>
                        
                        <%
                        }
						}
					
					%>
                     </div>
			</div>
		</div>
      </div>   
         
</body>
  

</html>