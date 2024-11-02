import Foundation

@objc public class PrintOptions: NSObject {
    private var name: String

    init(name: String) {
        self.name = name
    }

    func getName() -> String {
        return name
    }
}
