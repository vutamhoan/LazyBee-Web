<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.born2go.lazzybee.gdatabase.shared.Picture"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page import="com.born2go.lazzybee.gdatabase.server.DataServiceImpl"%>
<%@ page import="com.born2go.lazzybee.gdatabase.shared.Blog"%>
<%!//Global functions
	public void redirectHomeUrl(HttpServletResponse response) {
		String site = new String("/mvdict/");
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
	}%>
<%
	Picture blog_avatar = null;
   String title = "";
   Blog currentBlog = null;
   List<Blog> blogs_exsist = new ArrayList<Blog>();
   
	if (request.getPathInfo() == null
	|| request.getPathInfo().length() <= 1)
		redirectHomeUrl(response);
	else {
		String blogTitle = request.getPathInfo().replaceAll("/", "");
	if (blogTitle == null || blogTitle.equals(""))
redirectHomeUrl(response);
		else {
	        DataServiceImpl service = new DataServiceImpl();
	  currentBlog = service.findBlogByTitle(blogTitle);
	if (currentBlog == null)
		redirectHomeUrl(response);
	else {
		if (currentBlog.getAvatar() != null)
	blog_avatar = service.findPicture(currentBlog.getAvatar());
	 title = currentBlog.getShowTitle();
//	content = content.replaceAll("<p>&nbsp;</p>", "");
	blogs_exsist = service.getBlogsOlder(currentBlog);
	}
		}
	}
%>
<!doctype html>
<!-- The DOCTYPE declaration above will set the    -->
<!-- browser's rendering engine into               -->
<!-- "Standards Mode". Replacing this declaration  -->
<!-- with a "Quirks Mode" doctype may lead to some -->
<!-- differences in layout.                        -->
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<!-- for view mobile -->
<meta content="width=device-width, initial-scale=1.0, user-scalable=yes"
	name="viewport">
<title><%=title%></title>
<link type="text/css" rel="stylesheet"
	href="/mobile-resources/mobile.css">
<link rel="stylesheet"
	href="/resources/font-awesome-4.2.0/css/font-awesome.min.css">
<link rel="icon" type="image/png" href="/favicon.png" />
<script type="text/javascript"
	src="/lazzybeemobile/lazzybeemobile.nocache.js" async></script>
<!-- <script src="https://connect.facebook.net/en_US/all.js" async></script> -->
</head>
<body>
	<!-- Google Tag Manager -->
	<noscript>
		<iframe src="//www.googletagmanager.com/ns.html?id=GTM-KZBFX5"
			height="0" width="0" style="display: none; visibility: hidden"></iframe>
	</noscript>
	<script async>
		(function(w, d, s, l, i) {
			w[l] = w[l] || [];
			w[l].push({
				'gtm.start' : new Date().getTime(),
				event : 'gtm.js'
			});
			var f = d.getElementsByTagName(s)[0], j = d.createElement(s), dl = l != 'dataLayer' ? '&l='
					+ l
					: '';
			j.async = true;
			j.src = '//www.googletagmanager.com/gtm.js?id=' + i + dl;
			f.parentNode.insertBefore(j, f);
		})(window, document, 'script', 'dataLayer', 'GTM-KZBFX5');
	</script>
	<!-- End Google Tag Manager -->

	<div id="fb-root"></div>
	<!-- OPTIONAL: include this if you want history support -->
	<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
		style="position: absolute; width: 0; height: 0; border: 0"></iframe>
	<!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
	<noscript>
		<div
			style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
			Your web browser must have JavaScript enabled in order for this
			application to display correctly.</div>
	</noscript>
	<div class="header_w">
		<div class="header">
			<div class="left_header">
				<i class="fa fa-bars fa-lg" id="menuBtn"></i>
			</div>
			<div class="toppest">
				<div class="main_title">Lazzybee Blogs</div>
			</div>
		</div>
	</div>
	<div id="main">
		<div id="content">
			<div class="nameBlog">
				<h1><%=title%></h1>
			</div>
			<%
				SimpleDateFormat df = new SimpleDateFormat("d/MM/yyyy");
			%>
			<i class="fa fa-clock-o">&nbsp;</i><i class="publishdate"><%=df.format(new Date(currentBlog.getCreateDate()))%></i>

			<%
				if (blog_avatar != null) {
			%>
			<div class="avatarBlog" id="avatarBlog">
				<img alt="hoc cung lazzybee"
					src="<%=blog_avatar.getServeUrl() + "=s200"%>" height="200px">
			</div>
			<%
				}
			%>
			<div class="mCenter">
				<div><%=currentBlog.getContent()%></div>
				<br />
			</div>

			<%
				if (blogs_exsist.size() > 0) {
			%>
			<div class="fon39">
				<h5>Các bài đã đăng</h5>
			</div>
			<ul class="blogs_exist">
				<%
					for (int i = 0; i < blogs_exsist.size(); i++) {
																					Blog blog_exist = blogs_exsist.get(i);
				%>
				<li><a style="color: #004175; line-height: 2;"
					href=<%="/blog/" + blog_exist.getTitle()%>><%=blog_exist.getShowTitle()%></a></li>
				<%
					}
				%>
			</ul>
			<%
				}
			%>
			<script>
				function onFBReady() {
					var fbApi = new faceBookAPI();
					fbApi.OnloadFaceBook();
				}
			</script>
			<script type="text/javascript">
				function showmap(id, linkid) {
					var divid = document.getElementById(id);
					var toggleLink = document.getElementById(linkid);
					if (divid.style.display == 'block') {
						toggleLink.innerHTML = 'Hiển thị comments FB';
						divid.style.display = 'none';
					} else {
						toggleLink.innerHTML = 'Ẩn comments FB';
						divid.style.display = 'block';
						onFBReady();
					}
				}
			</script>
			<!-- hide/show fb-comments -->
			<a id="showComment" href="#" class="MTestTool_Obj11"
				onclick="showmap('fb_comments', this.id);">Hiển thị comment</a>
			<div id="fb_comments" style="display: none">
				<div class="fb-comments" data-width="100%"
					data-href="http://www.lazzybee.com/blog/<%=title%>"
					data-numposts="5" data-colorscheme="light"
					data-order-by="reverse_time" data-version="v2.3"></div>
			</div>
			<h2 class="mblog_install_app">
				Tải ứng dụng <a href="http://www.lazzybee.com/">Lazzybee</a> cho <a
					href="https://itunes.apple.com/us/app/lazzy-bee/id1035545961?ls=1&mt=8"
					style="cursor: none;">iOS</a> và <a
					href="https://play.google.com/store/apps/details?id=com.born2go.lazzybee"
					style="cursor: none;">Android</a>
			</h2>

			<br /> <br />
		</div>
	</div>
	<div class="mfooter" id="mfooter">
		<center>Born2Go©2015</center>
	</div>
</body>
</html>