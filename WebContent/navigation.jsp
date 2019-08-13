    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="login.jsp">0cloud0.com - Hosting</a>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            <!-- <li class="nav-item active">
              <a class="nav-link" href="https://8gwifi.org">Cryptography Playground</a>
            </li> -->
            <!-- <li class="nav-item active">
              <a class="nav-link"  href="https://www.linkedin.com/in/anishnath">Hire Me!</a>
            </li> -->
            
            <%
            
            String username = (String)session.getAttribute("user_name");
            if(username!=null)
            {
            	username = username.substring(0,username.lastIndexOf("@"));
            %>
            
            <li class="nav-item">
               <a class="nav-link"  href="Logout">Logout <%=username %></a>
            </li>
            
            <% } else {%>
             <li>
             <a class="nav-link active"  href="login.jsp">Login</a>
             </li>
            <%} %>
          </ul>
        </div>
      </div>
    </nav>
 
