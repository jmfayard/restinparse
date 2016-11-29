package com.github.jmfayard;

public class SelfieModel {
    public enum AndroidChatMessage implements ParseColumn {
        ACL,
        createdAt,
        objectId,
        updatedAt;


        public static ParseTable<AndroidChatMessage> table() {
            return new ParseTable<AndroidChatMessage>("AndroidChatMessage");
        }
    }


    public enum Attachment implements ParseColumn {
        ACL,
        createdAt,
        file,
        file2,
        imageUpload,
        objectId,
        sender,
        type,
        updatedAt;

        public static ParseTable<Attachment> table() {
            return new ParseTable<Attachment>("Attachment");
        }
    }


    public enum Blacklist implements ParseColumn {
        ACL,
        createdAt,
        device_id,
        email,
        objectId,
        updatedAt;

        public static ParseTable<Blacklist> table() {
            return new ParseTable<Blacklist>("Blacklist");
        }
    }


    public enum ChatAbstract implements ParseColumn {
        ACL,
        createdAt,
        deletedAt,
        lastMessage,
        lastMessageDate,
        objectId,
        partner,
        updatedAt;

        public static ParseTable<ChatAbstract> table() {
            return new ParseTable<ChatAbstract>("ChatAbstract");
        }
    }


    public enum ChatMessage implements ParseColumn {
        ACL,
        attachment,
        createdAt,
        deleted,
        message,
        messageHash,
        objectId,
        partner,
        sender,
        updatedAt;

        public static ParseTable<ChatMessage> table() {
            return new ParseTable<ChatMessage>("ChatMessage");
        }
    }


    public enum Comment implements ParseColumn {
        ACL,
        commentator,
        content,
        createdAt,
        objectId,
        updatedAt;

        public static ParseTable<Comment> table() {
            return new ParseTable<Comment>("Comment");
        }


    }


    public enum Event implements ParseColumn {
        ACL,
        actor,
        affects,
        amount,
        comment,
        createdAt,
        image,
        objectId,
        type,
        updatedAt;

        public static ParseTable<Event> table() {
            return new ParseTable<Event>("Event");
        }

    }


    public enum EventGroup implements ParseColumn {
        ACL,
        createdAt,
        events,
        images,
        isActor,
        objectId,
        type,
        updatedAt,
        user,
        users;

        public static ParseTable<EventGroup> table() {
            return new ParseTable<EventGroup>("EventGroup");
        }

    }


    public enum ImageUploadAdapter implements ParseColumn {
        ACL,
        blurredImage,
        buyers,
        comments,
        createdAt,
        deletedAt,
        description,
        file,
        fskReviewed,
        image,
        inflatedPrice,
        likers,
        objectId,
        originalImage,
        reportedAt,
        reporter,
        roses,
        status,
        tag,
        title,
        updatedAt,
        uploader,
        visibility;

        public static ParseTable<ImageUploadAdapter> table() {
            return new ParseTable<ImageUploadAdapter>("ImageUploadAdapter");
        }
    }


    public enum JobLog implements ParseColumn {
        ACL,
        createdAt,
        description,
        errors,
        objectId,
        objects,
        params,
        updatedAt,
        urls;

        public static ParseTable<ParseColumn> table() {
            return new ParseTable<ParseColumn>("ParseColumn");
        }
    }


    public enum Like implements ParseColumn {
        ACL,
        createdAt,
        image,
        objectId,
        updatedAt,
        user;

        public static ParseTable<ParseColumn> table() {
            return new ParseTable<ParseColumn>("ParseColumn");
        }
    }


    public enum PrivatePicturesRequest implements ParseColumn {
        ACL,
        createdAt,
        objectId,
        owner,
        permitted,
        requester,
        updatedAt;

        public static ParseTable<PrivatePicturesRequest> table() {
            return new ParseTable<PrivatePicturesRequest>("PrivatePicturesRequest");
        }

    }


    public enum ProfileVisit implements ParseColumn {
        ACL,
        createdAt,
        lastTimeNotified,
        objectId,
        updatedAt,
        visited,
        visitor,
        visitsCount;

        public static ParseTable<ProfileVisit> table() {
            return new ParseTable<ProfileVisit>("ProfileVisit");
        }


    }


    public enum PushesToSend implements ParseColumn {
        ACL,
        alreadySent,
        createdAt,
        objectId,
        text,
        toUser,
        updatedAt;

        public static ParseTable<PushesToSend> table() {
            return new ParseTable<PushesToSend>("PushesToSend");
        }

    }


    public enum Subscriptions implements ParseColumn {
        ACL,
        blocked,
        createdAt,
        objectId,
        subscriber,
        subscriberOf,
        updatedAt,
        useMasterKey;

        public static ParseTable<Subscriptions> table() {
            return new ParseTable<Subscriptions>("Subscriptions");
        }

    }


    public enum TestObject implements ParseColumn {
        ACL,
        createdAt,
        foo,
        objectId,
        updatedAt;

        public static ParseTable<TestObject> table() {
            return new ParseTable<TestObject>("TestObject");
        }

    }


    public enum UsageRight implements ParseColumn {
        ACL,
        createdAt,
        image,
        objectId,
        updatedAt,
        user;

        public static ParseTable<UsageRight> table() {
            return new ParseTable<UsageRight>("UsageRight");
        }


    }


    public enum UserBlocked implements ParseColumn {
        ACL,
        createdAt,
        email,
        objectId,
        updatedAt,
        user;

        public static ParseTable<UserBlocked> table() {
            return new ParseTable<UserBlocked>("UserBlocked");
        }


    }


    public enum WarnLog implements ParseColumn {
        ACL,
        createdAt,
        message,
        objectId,
        updatedAt,
        user;

        public static ParseTable<WarnLog> table() {
            return new ParseTable<WarnLog>("WarnLog");
        }


    }


    public enum _Installation implements ParseColumn {
        ACL,
        GCMSenderId,
        appIdentifier,
        appName,
        appVersion,
        badge,
        buildNumber,
        channels,
        createdAt,
        device,
        deviceToken,
        deviceType,
        installationId,
        localeIdentifier,
        objectId,
        parseVersion,
        pushType,
        timeZone,
        updatedAt,
        user;

        public static ParseTable<_Installation> table() {
            return new ParseTable<_Installation>("_Installation");
        }


    }


    public enum _Role implements ParseColumn {
        ACL,
        createdAt,
        name,
        objectId,
        roles,
        updatedAt,
        users;

        public static ParseTable<_Role> table() {
            return new ParseTable<_Role>("_Role");
        }


    }


    public enum _Session implements ParseColumn {
        ACL,
        createdAt,
        createdWith,
        expiresAt,
        installationId,
        objectId,
        restricted,
        sessionToken,
        updatedAt,
        user;

        public static ParseTable<_Session> table() {
            return new ParseTable<_Session>("_Session");
        }

    }


    public enum _User implements ParseColumn {
        campoint,
        campointData,
        _id,
        _created_at,
        _updated_at,
        ACL,
        acceptedPushes,
        authData,
        birthday,
        blocked,
        blockedUsers,
        callsAccepted,
        chatUser,
        countryCode,
        createdAt,
        dateFskCheck,
        deleteState,
        deletedAbstracts,
        device_id,
        email,
        emailBonusAlreadyPaid,
        emailLower,
        emailLowerCase,
        emailVerified,
        facebookId,
        fskCheckCompleted,
        fskCheckHandled,
        gender,
        hasContent,
        home,
        idCardImage,
        image,
        imageModified,
        imageThird,
        installation_id,
        lastImageSearches,
        lastTimeOnline,
        lastUserSearches,
        locality,
        numberIsVerified,
        objectId,
        password,
        phoneNumber,
        portraitImage,
        privacyMode,
        pushSent,
        rate,
        status,
        statusLong,
        updatedAt,
        username,
        validatedImage;

        public static ParseTable<_User> table() {
            return new ParseTable<_User>("_User");
        }


    }


    public enum TempChatMessage implements ParseColumn {
        ACL,
        createdAt,
        messageHash,
        objectId,
        receiver,
        sender,
        updatedAt;


        public static ParseTable<TempChatMessage> table() {
            return new ParseTable<TempChatMessage>("TempChatMessage");
        }


    }
}
