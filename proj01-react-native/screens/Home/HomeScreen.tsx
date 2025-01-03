import React, {useState} from 'react';
import { View, TextInput, Button } from 'react-native';

import { styles } from './HomeScreenStyles';

export function HomeScreen({navigation}:any) {
  const [nome, setNome] = useState("");
  function handleScreen (){
    navigation.navigate('Detail',{name:nome})
  }
  return (
    <View style={styles.container}>
        <TextInput 
          style={{
            backgroundColor:"#afde",
            color:"black",
            width:'80%',
            height:50,
            borderRadius:10,
            fontSize:23,
            borderWidth:3,
            borderColor:'black'
          }}
          placeholder='Digite seu Nome' 
          onChangeText={(value)=>setNome(value)} />
        <Button 
          title='Enviar texto e ir para segunda tela' 
          onPress={handleScreen}/>
    </View>
  );
}