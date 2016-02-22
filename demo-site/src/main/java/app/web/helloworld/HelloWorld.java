package app.web.helloworld;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Dylan
 */

@XmlAccessorType(value = XmlAccessType.FIELD)
public class HelloWorld {
    @XmlElement(name = "name")
    public String name;

}
