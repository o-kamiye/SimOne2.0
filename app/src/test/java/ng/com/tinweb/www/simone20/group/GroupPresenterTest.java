package ng.com.tinweb.www.simone20.group;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 12/09/2016.
 */
public class GroupPresenterTest {

    @Mock
    private IGroupView groupView;

    private GroupPresenter groupPresenter;

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        groupPresenter = new GroupPresenter(groupView);
    }

    @Test
    public void testSetGroupCount() {
        groupPresenter.setTotalGroupsCount();

        verify(groupView).setGroupsCountTextView(2);
    }

    @Test
    public void testSetGroupInfo() {
        // TODO add this test when this feature is implemented
    }

    @Test
    public void testEditGroup() {
        // TODO add edit group action here when implemented
    }

    @Test
    public void testDeleteGroup() {
        // TODO add delete group action here when implemented
    }

}