import { createBrowserRouter, RouterProvider } from "react-router-dom";
import RootLayout from './pages/Root';
import ChoresPage from "./pages/Chores";
import HouseManagersPage from './pages/HouseManagers';
import HouseMembersPage from './pages/HouseMembers';
import NewMemberPage from "./pages/NewMemberPage";
import { action as manipulateMemberAction } from './components/MemberForm'
import './App.css';


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
            element: <HouseMembersPage />
          },
          { 
            path: "newMember",
            element: <NewMemberPage />,
            action: manipulateMemberAction
          }
        ]
      },

      {
        path: "managers",
        element: <HouseManagersPage />
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