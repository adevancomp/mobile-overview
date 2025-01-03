import {createBottomTabNavigator} from  '@react-navigation/bottom-tabs';
import { Gallery } from '../Gallery/GalleryScreen';
import { Gallery2 } from '../Gallery2/GalleryScreen2';
import { MaterialCommunityIcons } from '@expo/vector-icons';

const {Screen, Navigator} = createBottomTabNavigator();

export function BottomTabsRoutes () {
    return (
        <Navigator>
            <Screen 
                name='g1' 
                component={Gallery} 
                options={{title:"Galeria 1", 
                tabBarIcon: ({color, size}) => (
                    <MaterialCommunityIcons 
                        name='power-off'
                        size={32} />
                )}}/>
            <Screen 
                name='g2' 
                component={Gallery2} 
                options={{title:"Galeria 2", 
                tabBarIcon: ({color, size}) => (
                    <MaterialCommunityIcons 
                        name='power-on'
                        size={32} />
                )}}/>
        </Navigator>
    )
}