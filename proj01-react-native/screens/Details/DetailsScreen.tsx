import React, {useState} from 'react';
import { View, Text, Button } from 'react-native';

import { styles } from './DetailsScreenStyles';

export function Details({route, navigation}: any) {
  const [texto, setTexto] = useState("Texto novo");
  const {name} = route.params
  function handleScreen(){
    navigation.navigate('Gallery')
  }
  return (
    <View style={styles.container}>
        <Text style={{fontSize:24}} onPress={()=>setTexto(name)} >{texto}</Text>
        <Button title='Ir para gallery' onPress={handleScreen}></Button>
    </View>
    
  );
}