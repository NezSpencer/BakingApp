package com.nezspencer.bakingapp;

import com.nezspencer.bakingapp.recipedashboard.RecipeListPresenter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by nezspencer on 6/7/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class RecipeListPresenterTest {

    private RecipeListPresenter testPresenter;
    @Mock
    private BakingInterface.RecipeActivityContract testContract;

    @Before
    public void setUp() throws Exception {
        testPresenter = new RecipeListPresenter(testContract);

    }

    public void shouldShowErrorMsgWhenRequestIsNotCompleted() throws Exception{

    }
}