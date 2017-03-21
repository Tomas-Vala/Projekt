package cz.muni.fi;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.*;

/**
 * Test class for LeaseManagerImpl
 */
public class LeaseManagerImplTest {
    private LeaseManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new LeaseManagerImpl();
    }

    @Test
    public void createLease() throws Exception {
        Lease lease = new LeaseBuilder().build();
        manager.createLease(lease);

        Long leaseId = lease.getId();
        assertThat(leaseId).isNotNull();
        Lease gotLease1 = manager.getLease(leaseId);
        assertThat(gotLease1).isNotNull();
        assertThat(lease).isEqualTo(gotLease1);
        assertThat(lease).isNotSameAs(gotLease1);

        lease = new LeaseBuilder().build();
        manager.createLease(lease);

        Lease gotLease2 = manager.getLease(lease.getId());
        assertThat(gotLease2).isNotNull();
        assertThat(lease).isEqualTo(gotLease2);
        assertThat(lease).isNotSameAs(gotLease2);

        assertThat(gotLease1).isNotEqualTo(gotLease2);
    }

    @Test
    public void addLeaseWithWrongParameters() throws Exception {
        try {
            manager.createLease(null);
            fail();
        } catch (IllegalArgumentException e) {
        }

        Lease lease = new LeaseBuilder().withId(1L).build();
        try {
            manager.createLease(lease);
            fail();
        } catch (IllegalArgumentException e) {
        }

        lease = new LeaseBuilder().withMovie(null).build();
        try {
            manager.createLease(lease);
            fail();
        } catch (IllegalArgumentException e) {
        }

        lease = new LeaseBuilder().withCustomer(null).build();
        try {
            manager.createLease(lease);
            fail();
        } catch (IllegalArgumentException e) {
        }

        lease = new LeaseBuilder().withPrice(null).build();
        try {
            manager.createLease(lease);
            fail();
        } catch (IllegalArgumentException e) {
        }

        lease = new LeaseBuilder().withDateOfRent(null).build();
        try {
            manager.createLease(lease);
            fail();
        } catch (IllegalArgumentException e) {
        }

        lease = new LeaseBuilder().withExpectedDateOfReturn(null).build();
        try {
            manager.createLease(lease);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void getLeaseWithWrongParameters() throws Exception {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            manager.getLease(null);
        });

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            manager.getLease(-1L);
        });
    }

    @Test
    public void updateLease() throws Exception {
        Lease lease = new LeaseBuilder().withPrice(200).build();
        Lease lease2 = new LeaseBuilder()
                .withPrice(150)
                .withDateOfRent(LocalDate.of(2001, Month.JANUARY, 1))
                .withDateOfReturn(LocalDate.of(2001, Month.JANUARY, 30))
                .withExpectedDateOfReturn(LocalDate.of(2001, Month.JANUARY, 31))
                .build();
        manager.createLease(lease);
        manager.createLease(lease2);
        Long leaseId = lease.getId();
        Long lease2Id = lease2.getId();

        lease = manager.getLease(leaseId);
        lease.setDateOfRent(LocalDate.of(2005, Month.FEBRUARY, 10));
        lease.setExpectedDateOfReturn(LocalDate.of(2005, Month.FEBRUARY, 28));
        lease.setDateOfReturn(null);
        manager.updateLease(lease);

        lease = manager.getLease(leaseId);
        assertThat(LocalDate.of(2005, Month.FEBRUARY, 10)).isEqualTo(lease.getDateOfRent());
        assertThat(LocalDate.of(2005, Month.FEBRUARY, 28)).isEqualTo(lease.getExpectedDateOfReturn());
        assertThat(lease.getDateOfReturn()).isNull();

        assertThat(new Integer(200)).isEqualTo(lease.getPrice());

        lease2 = manager.getLease(lease2Id);
        assertThat(new Integer(150)).isEqualTo(lease2.getPrice());
        assertThat(LocalDate.of(2001, Month.JANUARY, 1)).isEqualTo(lease2.getDateOfRent());
        assertThat(LocalDate.of(2001, Month.JANUARY, 30)).isEqualTo(lease2.getDateOfReturn());
        assertThat(LocalDate.of(2001, Month.JANUARY, 31)).isEqualTo(lease2.getExpectedDateOfReturn());
    }

    @Test
    public void updateLeaseWithWrongParameters() {
        Lease lease = new LeaseBuilder().build();
        Lease lease2 = new LeaseBuilder().build();
        manager.createLease(lease);
        manager.createLease(lease2);
        Long leaseId = lease.getId();
        Long lease2Id = lease2.getId();
    }

    @Test
    public void deleteLease() throws Exception {

    }

    @Test
    public void getAllLeases() throws Exception {

    }

    @Test
    public void findLeaseByCustomer() throws Exception {

    }

    @Test
    public void findLeaseByMovie() throws Exception {

    }

}