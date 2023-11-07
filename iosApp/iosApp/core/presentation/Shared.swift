//
//  Colors.swift
//  iosApp
//
//  Created by Jan Kotas on 09.10.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

class Shared {
    static let colors = Colors()
    
    class Colors {
        
        let lightBlue: SwiftUI.Color
        let textBlack: SwiftUI.Color
        let gradientGreyStart: SwiftUI.Color
        let gradientGreyEnd: SwiftUI.Color

        let primary: SwiftUI.Color
        let onPrimary: SwiftUI.Color
        let background: SwiftUI.Color
        let onBackground: SwiftUI.Color
        let surface: SwiftUI.Color
        let onSurface: SwiftUI.Color

        init() {
            lightBlue = Color(SharedRes.colors().lightBlue.getUIColor())
            textBlack = Color(SharedRes.colors().textBlack.getUIColor())
            gradientGreyStart = Color(SharedRes.colors().gradientGreyStart.getUIColor())
            gradientGreyEnd = Color(SharedRes.colors().gradientGreyEnd.getUIColor())

            primary = Color(SharedRes.colors().primary.getUIColor())
            onPrimary = Color(SharedRes.colors().onPrimary.getUIColor())
            background = Color(SharedRes.colors().background.getUIColor())
            onBackground = Color(SharedRes.colors().onBackground.getUIColor())
            surface = Color(SharedRes.colors().surface.getUIColor())
            onSurface = Color(SharedRes.colors().onSurface.getUIColor())
        }
    }
}
