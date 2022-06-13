package package1;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Objects;
@Entity(name="InvoiceItem")
@Table(name="invoice_item")
@XmlRootElement
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @XmlElement
    private BigDecimal cost;
    @Column
    @XmlElement
    private int count;
    @Column
    @XmlElement
    private BigDecimal amount;
    @Column
    @XmlElement
    private String name;


    public InvoiceItem(long id, String cost, int count, String name) {
        this.id=id;
        this.cost = new BigDecimal(cost);
        this.count = count;
        amount = this.cost.multiply(BigDecimal.valueOf(count));
        this.name = name;

    }

    public BigDecimal getCost() {
        return cost;
    }

    public int getCount() {
        return count;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Cost: " + cost + "\n" +
                "Count: " + count + "\n" +
                "Amount: " + amount + "\n" +
                "Name: " + name + "\n\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem invoice = (InvoiceItem) o;
        return count == invoice.count &&
                Objects.equals(cost, invoice.cost) &&
                Objects.equals(amount, invoice.amount) &&
                Objects.equals(name, invoice.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, count, amount, name);
    }

    public InvoiceItem() {
        this.cost = new BigDecimal(0);
        amount = new BigDecimal(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }
}
