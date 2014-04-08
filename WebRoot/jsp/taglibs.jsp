<%
String path = request.getContextPath();
            String basePath;
            if (request.getServerPort() == 80) {
                basePath = request.getScheme() + "://"
                        + request.getServerName() + path;
            } else {
                basePath = request.getScheme() + "://"
                        + request.getServerName() + ":"
                        + request.getServerPort() + path;
            }
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />