package package1;

import org.flywaydb.core.Flyway;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.xml.bind.*;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class main {

    public static void main(String[] args) throws JAXBException, IOException {

        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/taskHibernate", "postgres", "studentkaufy18").load();

        // Start the migration
        flyway.migrate();

        String s[] = {"bag", "phone", "paper", "pen"};
        int n[] = {2, 1, 4, 100};
        String c[] = {"200.2", "40000.99", "500", "23.5"};

        List<InvoiceItem> arr = new ArrayList<>();

        for (int i = 0; i < 4; ++i) {
            arr.add(new InvoiceItem(i + 1, c[i], n[i], s[i]));
        }


        String s1[] = {"note", "cat", "pen"};
        int n1[] = {1, 1, 1};
        String c1[] = {"666", "0.99", "5880"};

        List<InvoiceItem> arr1 = new ArrayList<>();

        for (int i = 0; i < 3; ++i) {
            arr1.add(new InvoiceItem(i + 1, c1[i], n1[i], s1[i]));
        }

        Invoice d1 = new Invoice(1, 1234567, Status.PAID, arr);
        Invoice d2 = new Invoice(2, 884567, Status.CANCELLED, arr1);

        JAXBContext context = JAXBContext.newInstance(Invoice.class, InvoiceItem.class, Status.class);
        Marshaller mar = context.createMarshaller();
        Unmarshaller um = context.createUnmarshaller();
        File file = new File("./invoice.xml");
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(d1, file);
        Invoice obj = (Invoice) um.unmarshal(file);
        //System.out.println(d1.equals(obj));
        System.out.println("Unmarshal:\n" + obj);


        context.generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String namespaceURI, String suggestedFileName)
                    throws IOException {
                return new StreamResult(suggestedFileName);
            }
        });

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            for (InvoiceItem i : arr)
                session.save(i);
            for (InvoiceItem i : arr1)
                session.save(i);

            session.save(d1);
            session.save(d2);

            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }


        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            long id = d1.getOrderID();
            session.beginTransaction();

            Invoice dbInvoice = session.get(Invoice.class, id);
            // System.out.println("Object from db:\n"+dbInvoice+"\n");
            //d1.equals(dbInvoice);

            System.out.println("Items from db:\n" + dbInvoice.getInvoiceItem());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();

        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            d1.setStatus(Status.UNPAID);
            d1.setDateOfChange(new Date());
            d2.setDateOfChange(new Date());
            session.update(d1);
            session.update(d2);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();

        }

    }
}