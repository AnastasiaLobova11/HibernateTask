package package1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
@XmlEnum
public enum Status {
    @XmlEnumValue("unpaid")
    UNPAID,
    @XmlEnumValue("paid")
    PAID,
    @XmlEnumValue("cancelled")
    CANCELLED,
    @XmlEnumValue("refunded")
    REFUNDED
}
