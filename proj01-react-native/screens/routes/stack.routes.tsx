import {createNativeStackNavigator} from '@react-navigation/native-stack'
import { HomeScreen } from '../Home/HomeScreen'
import { Details } from '../Details/DetailsScreen'
import { BottomTabsRoutes } from './bottom-tabs.routes'

const {Screen, Navigator} =  createNativeStackNavigator()

export function StackRoutes () {
    return (
        <Navigator>
            <Screen name='Home' component={HomeScreen}/>
            <Screen name='Detail' component={Details}/>
            <Screen name='Gallery' component={BottomTabsRoutes}/>
        </Navigator>
    )
}