import { createBrowserRouter, RouterProvider } from "react-router-dom";
import RootLayout from './pages/Root';
import ChoresPage from "./pages/Chores";
import HouseManagersPage from './pages/HouseManagers';
import HouseMembersPage from './pages/HouseMembers';
import NewMemberPage from "./pages/NewMemberPage";
import HouseMembersLandingPage from "./pages/HouseMembersLandingPage";
import { action as manipulateMemberAction } from './components/MemberForm'
import { action as manipulateManagerAction } from './components/ManagerForm'
import { loader as loadMembers } from './pages/HouseMembers'
import './App.css';
import HouseManagersLandingPage from "./pages/HouseManagersLandingPage";



const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    children: [
      {
        path: "chores",
        element: <ChoresPage/>
      },

      {
        path: "members",
        children: [
          {
            index: true,
            element: <HouseMembersLandingPage />,
          },
          { 
            path: "newMember",
            element: <NewMemberPage />,
            action: manipulateMemberAction
          }, 
          {
            path: "viewMembers",
            element: <HouseMembersPage />,
            loader: loadMembers
          }
        ]
      },
      {
        path: "/managers",
        element: <HouseManagersLandingPage />,
        children: [
          {
            index: true,
            path: "newManager",
            element: <HouseManagersLandingPage />,
            action: manipulateManagerAction
          }, 
        ]
      }
    ]
  }
])

function App() {
  return (
    <RouterProvider router={router} />
  );
}

export default App;
