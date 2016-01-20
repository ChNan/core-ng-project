package http;

import util.Charsets;

import java.nio.charset.Charset;

/**
 * @author Dylan
 */
public class ContentType {
    public static final ContentType TEXT_HTML = create("text/html", Charsets.UTF_8);
    public static final ContentType TEXT_CSS = create("text/css", Charsets.UTF_8);
    public static final ContentType TEXT_XML = create("text/xml", Charsets.UTF_8);
    public static final ContentType TEXT_PLAIN = create("text/plain", Charsets.UTF_8);
    public static final ContentType APPLICATION_JSON = create("application/json", Charsets.UTF_8);
    public static final ContentType APPLICATION_JAVASCRIPT = create("application/javascript", Charsets.UTF_8);
    public static final ContentType APPLICATION_OCTET_STREAM = create("application/octet-stream", null);

    public static ContentType create(String mediaType, Charset charset) {
        String contentType = charset == null ? mediaType : mediaType + ";charset=" + charset.toString();
        return new ContentType(contentType, mediaType, charset);
    }

    // application/json;charset=utf-8
    // application/octet-stream
    public static ContentType parse(String contentType) {
        String mediaType = contentType;
        int firstSemicolon = contentType.indexOf(';');
        Charset charset = null;
        if (firstSemicolon > 0) {
            mediaType = contentType.substring(0, firstSemicolon);
            int charsetIndex = contentType.indexOf("charset;", firstSemicolon + 1);
            charset = Charset.forName(contentType.substring(charsetIndex + 8));
        }
        return new ContentType(contentType, mediaType, charset);

    }

    private String contentType;
    private String mediaType;
    private Charset charset;

    public ContentType(String contentType, String mediaType, Charset charset) {
        this.contentType = contentType;
        this.mediaType = mediaType;
        this.charset = charset;
    }

    public String mediaType() {
        return mediaType;
    }

    public Charset charset() {
        return charset;
    }

    public String toString() {
        return contentType;
    }

}
