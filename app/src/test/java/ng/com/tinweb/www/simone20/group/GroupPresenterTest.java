package ng.com.tinweb.www.simone20.group;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import ng.com.tinweb.www.simone20.data.group.SimOneGroup;

import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 12/09/2016.
 */
public class GroupPresenterTest {

    @Mock
    private GroupContract.View view;

    @Mock
    private SimOneGroup simOneGroup;

    @Captor
    private ArgumentCaptor<SimOneGroup.GetAllCallback> getAllArgumentCaptor;

    private GroupPresenter groupPresenter;

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        groupPresenter = new GroupPresenter(simOneGroup, view);
    }

    @Test
    public void testLoadGroupsSuccess() {
        List<SimOneGroup> groups = new ArrayList<>();
        groupPresenter.loadGroups();
        verify(simOneGroup).getAll(getAllArgumentCaptor.capture());
        getAllArgumentCaptor.getValue().onSuccess(groups);
        verify(view).onGroupsLoaded(groups);
    }

    @Test
    public void testLoadGroupsError() {
        groupPresenter.loadGroups();
        verify(simOneGroup).getAll(getAllArgumentCaptor.capture());
        getAllArgumentCaptor.getValue().onError(2);
        verify(view).onGroupsLoadingError("An unknown error occurred");
    }

    @Test
    public void testSetGroupInfo() {
        // TODO add this test when this feature is implemented
    }

    @Test
    public void testDeleteGroup() {
        // TODO add delete group action here when implemented
    }

}