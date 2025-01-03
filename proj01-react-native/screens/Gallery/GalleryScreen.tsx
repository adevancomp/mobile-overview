import React from 'react';
import { View, Text } from 'react-native';

import { styles } from './GalleryStyles';

export function Gallery() {
  return (
    <View style={styles.container}>
        <Text style={{fontSize:24}} >Gallery Screen</Text>
    </View>
  );
}