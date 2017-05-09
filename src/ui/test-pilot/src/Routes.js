import React from 'react'
import {Route, Router, browserHistory, IndexRoute} from 'react-router'

import ListPlansPage from './ListPlans/ListPlansPage'
import CreateTestPage from './CreateTest/CreateTestPage'
import CreateRulePage from './CreateRule/CreateRulePage'

export default function getRoutes() {

  return (
    <Router history={browserHistory}>
      <Route path="/" component={ListPlansPage} />
      <Route path="/add" component={CreateTestPage}/>
      <Route path="/:planId/addRule" component={({children}) => children}>
        <IndexRoute component={CreateRulePage} />
        <Route path="planId" component={CreateRulePage} />
      </Route>
    </Router>
  )
}