package com.xingkaichun.utils;

public class CommonUtilsHtml {

    public static String handlerArticleContent(String html){
        if(!CommonUtilsString.isNullOrEmpty(html)){
            html = html.replaceAll("</div><","</div>\r\n<");
            html = html.replaceAll("</p><","</p>\r\n<");
            html = html.replaceAll("><tbody>",">\r\n<tbody>");
            html = html.replaceAll("<tbody><","<tbody>\r\n<");
            html = html.replaceAll("</tbody><","</tbody>\r\n<");
            html = html.replaceAll("</table><","</table>\r\n<");
            html = html.replaceAll("</tr><","</tr>\r\n<");
            html = html.replaceAll("<tr><","<tr>\r\n<");
            html = html.replaceAll("</td><","</td>\r\n<");
            html = html.replaceAll("</h1><","</h1>\r\n<");
            html = html.replaceAll("</h2><","</h2>\r\n<");
            html = html.replaceAll("</h3><","</h3>\r\n<");
            html = html.replaceAll("</h4><","</h4>\r\n<");
            html = html.replaceAll("</h5><","</h5>\r\n<");
            html = html.replaceAll("</h6><","</h6>\r\n<");
            html = html.replaceAll("</h7><","</h7>\r\n<");
        }
        return html;
    }
}
