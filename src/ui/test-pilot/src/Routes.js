import React from 'react'
import {Route, Router, browserHistory} from 'react-router'

import ListPlansPage from './ListPlans/ListPlansPage'
import CreateTestPage from './CreateTest/CreateTestPage'

export default function getRoutes() {

  return (
    <Router history={browserHistory}>
      <Route path="/" component={ListPlansPage} />
      <Route path="/add" component={CreateTestPage}/>
    </Router>
  )
}