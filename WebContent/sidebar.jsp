<div class="bg-light border-right" id="sidebar-wrapper">
      
      <div class="sidebar-heading"></div>
      
      <div class="list-group list-group-flush">
         <a href="index.jsp" class="list-group-item list-group-item-action bg-light">Home</a>
      </div>
      
                 
            <%
            
            String x = (String)session.getAttribute("user_name");
            if(x!=null)
            {
            	
            %>
            
            <div class="list-group list-group-flush">
               <a  class="list-group-item list-group-item-action bg-light"  href="Logout">Logout </a>
			</div>
            
            <% } else {%>
             <div class="list-group list-group-flush">
             <a class="list-group-item list-group-item-action bg-light" href="login.jsp">Login</a>
             </div>
            <%} %>


      
      
        
      

     <div class="list-group list-group-flush">
        <a href="add.jsp" class="list-group-item list-group-item-action bg-light">Container Service</a>
      </div>
      
      
     <div class="list-group list-group-flush">
        <a href="playground" class="list-group-item list-group-item-action bg-light">Terminal Service</a>
      </div>
      
      <div class="list-group list-group-flush">
        <a href="sql.jsp" class="list-group-item list-group-item-action bg-light">MySQL Service</a>
      </div>
      
	<div class="list-group list-group-flush">
        <a href="contactus.jsp" class="list-group-item list-group-item-action bg-light">Contact Me</a>
      </div>
      
      <div class="list-group list-group-flush">
        <a href="https://www.linkedin.com/in/anishnath" target="_blank" class="list-group-item list-group-item-action bg-light">Hire Me!!</a>
      </div>
       <div class="list-group list-group-flush">
        <a href="https://8gwifi.org" target="_blank" class="list-group-item list-group-item-action bg-light">Cryptography Playground</a>
      </div>
      
    </div>