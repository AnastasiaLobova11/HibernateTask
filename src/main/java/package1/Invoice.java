package package1;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "Invoice")
@Table(name = "invoice")
@XmlRootElement
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", unique = true)
    @XmlElement
    private long orderID;
    @Column(name = "date_of_create")
    @XmlElement
    private Date dateOfCreate;
    @Column(name = "date_of_change")
    @XmlElement
    private Date dateOfChange;
    @XmlElement
    private BigDecimal amount = new BigDecimal(0);
    @Column(name = "client_id")
    @XmlElement
    private Integer clientID;
    @Column
    @XmlElement
    @Enumerated(EnumType.STRING)
    private Status status;


    @OneToMany
    @JoinColumn(name = "order_id")
    @Column
    @XmlElement
    private List<InvoiceItem> items = new ArrayList<>();

    public Invoice() {
        dateOfCreate = new Date();
    }

    public Invoice(long orderID, int clientID, Status status, List<InvoiceItem> invoiceItem) {
        this.orderID = orderID;
        dateOfCreate = new Date();
        this.clientID = clientID;
        this.items = invoiceItem;
        this.status = status;
        for (InvoiceItem i : invoiceItem) {
            amount = amount.add(i.getAmount());
        }
    }

    public String invoiceItem() {
        String s = "";
        for (InvoiceItem i : items)
            s += i.toString();
        return s;
    }

    @Override
    public String toString() {
        String dC = "Not changed";
        if (dateOfChange != null) {
            dC = dateOfChange.toString();
        }
        return "Client ID: " + clientID + "\n"
                + "Order ID: " + orderID + "\n"
                + "Date of create: " + dateOfCreate + "\n"
                + "Date of change:" + dC + "\n"
                + "package1.Status: " + status + "\n"
                + "Amount: " + amount + "\n"
                + "Positions:\n\n" +
                invoiceItem();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice dto = (Invoice) o;
        boolean f = items.equals(dto.items);
        boolean f1 = clientID.equals(dto.clientID);
        boolean f2 = Objects.equals(dateOfCreate, dto.dateOfCreate);
        boolean f3 = Objects.equals(dateOfChange, dto.dateOfChange);
        boolean f4 = Objects.equals(amount, dto.amount) && status == dto.status;
        return f && f1 && f2 && f3 && f4;

    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dateOfCreate, dateOfChange, amount, clientID, status);
        result = 31 * result + items.hashCode();
        return result;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public Date getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(Date dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<InvoiceItem> getInvoiceItem() {
        return items;
    }

    public void setInvoiceItem(List<InvoiceItem> invoiceItem) {
        this.items = invoiceItem;
    }
}
