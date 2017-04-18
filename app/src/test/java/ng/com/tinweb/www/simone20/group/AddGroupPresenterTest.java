package ng.com.tinweb.www.simone20.group;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ng.com.tinweb.www.simone20.data.group.SimOneGroup;

import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.GROUP_EXISTS_ERROR;
import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 02/11/2016.
 */
public class AddGroupPresenterTest {

    @Mock
    private DialogFragmentContract.View view;

    @Mock
    private SimOneGroup simOneGroup;

    @Captor
    private ArgumentCaptor<SimOneGroup.ActionCallback> callbackArgumentCaptor;

    private GroupPresenter.AddGroupPresenter addGroupPresenter;

    private String groupName;
    private int groupInterval;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        addGroupPresenter = new GroupPresenter.AddGroupPresenter(view, simOneGroup);
        groupName = "Test";
        groupInterval = 7;
    }

    @Test
    public void testAddGroupSuccess() {
        addGroupPresenter.addGroup(groupName, groupInterval);

        verify(simOneGroup).setName(groupName);
        verify(simOneGroup).setInterval(groupInterval);
        verify(simOneGroup).save(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onSuccess();
        verify(view).onAddGroupSuccess();
    }

    @Test
    public void testAddGroupError_FromCallback() {
        addGroupPresenter.addGroup(groupName, groupInterval);

        verify(simOneGroup).setName(groupName);
        verify(simOneGroup).setInterval(groupInterval);
        verify(simOneGroup).save(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onError(GROUP_EXISTS_ERROR);
        verify(view).onAddGroupError("You have a group with the same name already");
    }

    @Test
    public void testAddGroupError_FromInput() {
        addGroupPresenter.addGroup("", 0);

        verify(view).onAddGroupError("Group name and interval should not be empty");
    }

}