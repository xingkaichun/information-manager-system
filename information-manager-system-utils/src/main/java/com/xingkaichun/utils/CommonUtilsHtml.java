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
        }
        return html;
    }
}
