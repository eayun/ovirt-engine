package org.ovirt.engine.core.bll;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.ovirt.engine.core.common.businessentities.VDSGroup;
import org.ovirt.engine.core.common.queries.StoragePoolQueryParametersBase;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dao.VdsGroupDAO;

/** A test case for {@link GetVdsGroupsByStoragePoolIdQuery} */
public class GetVdsGroupsByStoragePoolIdQueryTest extends AbstractUserQueryTest<StoragePoolQueryParametersBase, GetVdsGroupsByStoragePoolIdQuery<StoragePoolQueryParametersBase>> {

    /** Tests the flow of {@link GetVdsGroupsByStoragePoolIdQuery#executeQueryCommand()} using mock objects */
    @Test
    public void testExecuteQueryCommand() {
        // Set up the result
        Guid storagePoolId = Guid.NewGuid();
        VDSGroup group = new VDSGroup();
        group.setstorage_pool_id(storagePoolId);
        List<VDSGroup> result = Collections.singletonList(group);

        // Set up the query parameters
        when(getQueryParameters().getStoragePoolId()).thenReturn(storagePoolId);

        // Mock the DAO
        VdsGroupDAO vdsGroupDAOMock = mock(VdsGroupDAO.class);
        when(vdsGroupDAOMock.getAllForStoragePool(storagePoolId,
                getUser().getUserId(),
                getQueryParameters().isFiltered())).
                thenReturn(result);
        when(getDbFacadeMockInstance().getVdsGroupDAO()).thenReturn(vdsGroupDAOMock);

        // Execute the query
        getQuery().executeQueryCommand();

        // Check the result
        assertEquals("Wrong query result", result, getQuery().getQueryReturnValue().getReturnValue());
    }
}
